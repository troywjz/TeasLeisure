package com.zbkj.service.service.impl.tl.conversation;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.Stall;
import com.zbkj.common.model.tl.conversation.*;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.conversation.MessageSendRequest;
import com.zbkj.common.request.tl.conversation.MessageReportRequest;
import com.zbkj.common.response.tl.conversation.TlConversationResponse;
import com.zbkj.common.response.tl.conversation.TlMessageResponse;
import com.zbkj.common.utils.SecurityUtil;
import com.zbkj.service.dao.tl.StallDao;
import com.zbkj.service.dao.UserDao;
import com.zbkj.service.dao.tl.conversation.*;
import com.zbkj.service.service.UserService;
import com.zbkj.service.service.tl.conversation.TlConversationService;
import com.zbkj.service.service.tl.TlModerationEventService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class TlConversationServiceImpl extends ServiceImpl<TlConversationDao, TlConversation> implements TlConversationService {
    @Resource private TlConversationDao conversationDao;
    @Resource private TlMessageDao messageDao;
    @Resource private TlUserBlockDao blockDao;
    @Resource private TlMessageReportDao reportDao;
    @Resource private StallDao stallDao;
    @Resource private UserDao userDao;
    @Resource private UserService userService;
    @Resource private TlModerationEventService moderationEventService;
    @Value("${tl.messaging.send-limit:12}") private int sendLimit;
    @Value("${tl.messaging.send-window-seconds:60}") private int sendWindowSeconds;
    @Value("${tl.messaging.report-limit:5}") private int reportLimit;
    @Value("${tl.messaging.report-window-seconds:3600}") private int reportWindowSeconds;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long open(Long stallId) {
        Integer visitorId = userService.getUserIdException();
        Stall stall = stallDao.selectById(stallId);
        if (stall == null || !Integer.valueOf(1).equals(stall.getAuditStatus()) || stall.getOwnerUserId() == null || Boolean.TRUE.equals(stall.getIsDeleted())) {
            throw new IllegalArgumentException("摊位尚未认证摊主或暂不可联系");
        }
        if (visitorId.equals(stall.getOwnerUserId())) throw new IllegalArgumentException("摊主无法向自己发送私信");
        TlConversation existing = conversationDao.selectOne(Wrappers.<TlConversation>lambdaQuery()
                .eq(TlConversation::getStallId, stallId).eq(TlConversation::getVisitorUserId, visitorId));
        if (existing != null) return existing.getId();
        TlConversation conversation = new TlConversation().setStallId(stallId)
                .setVisitorUserId(visitorId).setOwnerUserId(stall.getOwnerUserId())
                .setCreateTime(new Date()).setUpdateTime(new Date()).setIsClosed(false);
        try { conversationDao.insert(conversation); }
        catch (DuplicateKeyException duplicate) {
            TlConversation raced = conversationDao.selectOne(Wrappers.<TlConversation>lambdaQuery()
                    .eq(TlConversation::getStallId, stallId).eq(TlConversation::getVisitorUserId, visitorId));
            if (raced == null) throw duplicate;
            return raced.getId();
        }
        return conversation.getId();
    }

    @Override
    public List<TlConversationResponse> listMine() {
        Integer userId = userService.getUserIdException();
        return conversationDao.selectMineSummaries(userId);
    }

    @Override
    public Integer unreadCount() {
        return listMine().stream().mapToInt(item -> item.getUnreadCount() == null ? 0 : item.getUnreadCount()).sum();
    }

    @Override
    public PageInfo<TlMessageResponse> messages(Long conversationId, PageParamRequest page) {
        Integer userId = userService.getUserIdException();
        requireMember(conversationId, userId);
        Page<TlMessage> pageInfo = PageHelper.startPage(page.getPage(), page.getLimit());
        List<TlMessage> rows = messageDao.selectList(Wrappers.<TlMessage>lambdaQuery()
                .eq(TlMessage::getConversationId, conversationId).eq(TlMessage::getIsDeleted, false)
                .orderByDesc(TlMessage::getId));
        List<TlMessageResponse> result = rows == null ? Collections.emptyList() : rows.stream()
                .map(row -> new TlMessageResponse().setId(row.getId()).setContent(row.getContent())
                        .setCreateTime(row.getCreateTime()).setMine(userId.equals(row.getSenderUserId())))
                .collect(java.util.stream.Collectors.toList());
        return CommonPage.copyPageInfo(pageInfo, result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long send(Long conversationId, MessageSendRequest request) {
        Integer userId = userService.getUserIdException();
        if (sendLimit < 1) throw new IllegalStateException("消息频控条数配置无效");
        Date windowStart = windowStart(sendWindowSeconds);
        lockUserRow(userId);
        TlConversation conversation = requireMember(conversationId, userId);
        Integer otherUserId = userId.equals(conversation.getVisitorUserId()) ? conversation.getOwnerUserId() : conversation.getVisitorUserId();
        if (!isSendAllowed(conversation, userId, otherUserId)) throw new IllegalStateException("当前会话已暂停发送");
        Integer sentLastMinute = Math.toIntExact(messageDao.selectCount(Wrappers.<TlMessage>lambdaQuery()
                .eq(TlMessage::getSenderUserId, userId).ge(TlMessage::getCreateTime, windowStart)));
        if (sentLastMinute != null && sentLastMinute >= sendLimit) throw new IllegalStateException("发送过于频繁，请稍后再试");
        TlMessage message = new TlMessage().setConversationId(conversationId).setSenderUserId(userId)
                .setContent(request.getContent().trim()).setCreateTime(new Date()).setIsDeleted(false);
        messageDao.insert(message);
        conversationDao.update(null, Wrappers.<TlConversation>lambdaUpdate().eq(TlConversation::getId, conversationId)
                .set(TlConversation::getLastMessageId, message.getId()).set(TlConversation::getLastMessageTime, message.getCreateTime())
                .set(TlConversation::getUpdateTime, new Date()));
        return message.getId();
    }

    @Override
    public void markRead(Long conversationId, Long messageId) {
        Integer userId = userService.getUserIdException();
        TlConversation conversation = requireMember(conversationId, userId);
        TlMessage target = messageDao.selectOne(Wrappers.<TlMessage>lambdaQuery().eq(TlMessage::getId, messageId)
                .eq(TlMessage::getConversationId, conversationId).eq(TlMessage::getIsDeleted, false));
        if (target == null) throw new IllegalArgumentException("消息不存在");
        if (userId.equals(conversation.getVisitorUserId())) {
            conversationDao.update(null, Wrappers.<TlConversation>lambdaUpdate().eq(TlConversation::getId, conversationId)
                    .and(q -> q.isNull(TlConversation::getVisitorReadMessageId)
                            .or().lt(TlConversation::getVisitorReadMessageId, messageId))
                    .set(TlConversation::getVisitorReadMessageId, messageId));
        } else {
            conversationDao.update(null, Wrappers.<TlConversation>lambdaUpdate().eq(TlConversation::getId, conversationId)
                    .and(q -> q.isNull(TlConversation::getOwnerReadMessageId)
                            .or().lt(TlConversation::getOwnerReadMessageId, messageId))
                    .set(TlConversation::getOwnerReadMessageId, messageId));
        }
    }

    @Override
    public void block(Long conversationId, boolean blocked) {
        Integer userId = userService.getUserIdException();
        TlConversation conversation = requireMember(conversationId, userId);
        Integer targetUserId = otherUserId(conversation, userId);
        if (targetUserId == null || targetUserId.equals(userId)) throw new IllegalArgumentException("屏蔽对象无效");
        if (blocked) {
            TlUserBlock row = new TlUserBlock().setUserId(userId).setBlockedUserId(targetUserId).setCreateTime(new Date());
            try { blockDao.insert(row); } catch (DuplicateKeyException ignored) { }
        } else {
            blockDao.delete(Wrappers.<TlUserBlock>lambdaQuery().eq(TlUserBlock::getUserId, userId)
                    .eq(TlUserBlock::getBlockedUserId, targetUserId));
        }
    }

    @Override
    public Boolean isBlockedInConversation(Long conversationId) {
        Integer userId = userService.getUserIdException();
        TlConversation conversation = requireMember(conversationId, userId);
        Integer targetUserId = otherUserId(conversation, userId);
        if (targetUserId == null || targetUserId.equals(userId)) throw new IllegalArgumentException("屏蔽对象无效");
        return blockDao.selectCount(Wrappers.<TlUserBlock>lambdaQuery()
                .eq(TlUserBlock::getUserId, userId).eq(TlUserBlock::getBlockedUserId, targetUserId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long report(MessageReportRequest request) {
        Integer userId = userService.getUserIdException();
        if (reportLimit < 1) throw new IllegalStateException("举报频控条数配置无效");
        Date windowStart = windowStart(reportWindowSeconds);
        lockUserRow(userId);
        requireMember(request.getConversationId(), userId);
        if (request.getMessageId() != null) {
            TlMessage message = messageDao.selectOne(Wrappers.<TlMessage>lambdaQuery().eq(TlMessage::getId, request.getMessageId())
                    .eq(TlMessage::getConversationId, request.getConversationId()));
            if (message == null) throw new IllegalArgumentException("举报消息不属于此会话");
        }
        Integer reportsInWindow = Math.toIntExact(reportDao.selectCount(Wrappers.<TlMessageReport>lambdaQuery()
                .eq(TlMessageReport::getReporterUserId, userId)
                .ge(TlMessageReport::getCreateTime, windowStart)));
        if (reportsInWindow != null && reportsInWindow >= reportLimit) throw new IllegalStateException("举报过于频繁，请稍后再试");
        TlMessageReport report = new TlMessageReport().setConversationId(request.getConversationId())
                .setMessageId(request.getMessageId()).setReporterUserId(userId).setReason(request.getReason().trim())
                .setStatus(0).setCreateTime(new Date());
        reportDao.insert(report);
        return report.getId();
    }

    @Override
    public PageInfo<TlMessageReport> pendingReports(PageParamRequest page) {
        Page<TlMessageReport> pageInfo = PageHelper.startPage(page.getPage(), page.getLimit());
        List<TlMessageReport> rows = reportDao.selectList(Wrappers.<TlMessageReport>lambdaQuery()
                .eq(TlMessageReport::getStatus, 0).orderByAsc(TlMessageReport::getCreateTime));
        return CommonPage.copyPageInfo(pageInfo, rows == null ? Collections.emptyList() : rows);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleReport(Long reportId, boolean resolve, String note) {
        TlMessageReport report = reportDao.selectById(reportId);
        if (report == null || !Integer.valueOf(0).equals(report.getStatus())) {
            throw new IllegalStateException("举报不存在或已处理");
        }
        Integer adminId = SecurityUtil.getLoginUserVo().getUser().getId();
        int updated = reportDao.update(null, Wrappers.<TlMessageReport>lambdaUpdate()
                .eq(TlMessageReport::getId, reportId).eq(TlMessageReport::getStatus, 0)
                .set(TlMessageReport::getStatus, resolve ? 1 : 2)
                .set(TlMessageReport::getHandledBy, adminId)
                .set(TlMessageReport::getHandleNote, note)
                .set(TlMessageReport::getHandleTime, new Date()));
        if (updated != 1) throw new IllegalStateException("举报不存在或已处理");
        moderationEventService.recordAdmin("message_report", reportId, report.getMessageId(), 0,
                resolve ? 1 : 2, resolve ? "resolve" : "dismiss", note);
    }

    private TlConversation requireMember(Long conversationId, Integer userId) {
        TlConversation conversation = conversationDao.selectOne(Wrappers.<TlConversation>lambdaQuery()
                .eq(TlConversation::getId, conversationId)
                .and(q -> q.eq(TlConversation::getVisitorUserId, userId).or().eq(TlConversation::getOwnerUserId, userId)));
        if (conversation == null) throw new IllegalArgumentException("会话不存在或无访问权限");
        return conversation;
    }

    private boolean isBlocked(Integer userId, Integer otherUserId) {
        return blockDao.selectCount(Wrappers.<TlUserBlock>lambdaQuery().and(q -> q
                .eq(TlUserBlock::getUserId, userId).eq(TlUserBlock::getBlockedUserId, otherUserId)
                .or().eq(TlUserBlock::getUserId, otherUserId).eq(TlUserBlock::getBlockedUserId, userId))) > 0;
    }

    private Integer otherUserId(TlConversation conversation, Integer userId) {
        return userId.equals(conversation.getVisitorUserId())
                ? conversation.getOwnerUserId() : conversation.getVisitorUserId();
    }

    private void lockUserRow(Integer userId) {
        if (userDao.lockUserRowById(userId) == null) throw new IllegalStateException("当前用户不存在");
    }

    private Date windowStart(int windowSeconds) {
        if (windowSeconds < 1) throw new IllegalStateException("消息频控时间窗口配置无效");
        return new Date(System.currentTimeMillis() - windowSeconds * 1000L);
    }

    private boolean isSendAllowed(TlConversation conversation, Integer userId, Integer otherUserId) {
        if (Boolean.TRUE.equals(conversation.getIsClosed())) return false;
        Stall stall = stallDao.selectById(conversation.getStallId());
        if (stall == null || !Integer.valueOf(1).equals(stall.getAuditStatus())
                || Boolean.TRUE.equals(stall.getIsDeleted()) || stall.getOwnerUserId() == null
                || !stall.getOwnerUserId().equals(conversation.getOwnerUserId())) return false;
        return !isBlocked(userId, otherUserId);
    }
}
