package com.zbkj.service.service.impl.tl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.TlModerationEvent;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.utils.SecurityUtil;
import com.zbkj.service.dao.tl.TlModerationEventDao;
import com.zbkj.service.service.tl.TlModerationEventService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TlModerationEventServiceImpl extends ServiceImpl<TlModerationEventDao, TlModerationEvent>
        implements TlModerationEventService {
    @Resource
    private TlModerationEventDao eventDao;

    @Override
    public void recordAdmin(String targetType, Long targetId, Long relatedId, Integer fromStatus,
                            Integer toStatus, String action, String reason) {
        com.zbkj.common.vo.LoginUserVo login = SecurityUtil.getLoginUserVo();
        if (login == null || login.getUser() == null || login.getUser().getId() == null) {
            throw new IllegalStateException("管理员身份缺失，无法记录审核事件");
        }
        recordActor(targetType, targetId, relatedId, fromStatus, toStatus, action, reason,
                "admin", login.getUser().getId(), login.getUser().getAccount());
    }

    @Override
    public void recordActor(String targetType, Long targetId, Long relatedId, Integer fromStatus,
                            Integer toStatus, String action, String reason, String actorType,
                            Integer actorId, String actorAccount) {
        if (reason != null && reason.length() > 500) {
            throw new IllegalArgumentException("审核说明最多500字");
        }
        if (reason != null) reason = reason.trim();
        if (actorId == null || actorAccount == null || actorAccount.trim().isEmpty()) {
            throw new IllegalStateException("操作人身份缺失，无法记录账号状态事件");
        }
        eventDao.insert(new TlModerationEvent().setTargetType(targetType).setTargetId(targetId)
                .setRelatedId(relatedId).setFromStatus(fromStatus).setToStatus(toStatus).setAction(action)
                .setActorType(actorType).setActorId(actorId).setActorAccount(actorAccount)
                .setReason(reason).setCreateTime(new Date()));
    }

    @Override
    public PageInfo<TlModerationEvent> list(PageParamRequest page, String targetType, Long targetId) {
        Page<TlModerationEvent> paging = PageHelper.startPage(page.getPage(), page.getLimit());
        List<TlModerationEvent> rows = eventDao.selectList(Wrappers.<TlModerationEvent>lambdaQuery()
                .eq(targetType != null && !targetType.trim().isEmpty(), TlModerationEvent::getTargetType, targetType)
                .eq(targetId != null, TlModerationEvent::getTargetId, targetId)
                .orderByDesc(TlModerationEvent::getCreateTime).orderByDesc(TlModerationEvent::getId));
        return com.zbkj.common.page.CommonPage.copyPageInfo(paging, rows);
    }
}
