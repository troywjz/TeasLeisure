package com.zbkj.service.service.impl.tl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.Stall;
import com.zbkj.common.model.tl.TlContentReport;
import com.zbkj.common.model.tl.community.TlComment;
import com.zbkj.common.model.tl.community.TlPost;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.TlContentReportRequest;
import com.zbkj.common.utils.SecurityUtil;
import com.zbkj.service.dao.tl.StallDao;
import com.zbkj.service.dao.tl.TlContentReportDao;
import com.zbkj.service.dao.tl.community.TlCommentDao;
import com.zbkj.service.dao.tl.community.TlPostDao;
import com.zbkj.service.service.UserService;
import com.zbkj.service.service.tl.TlContentReportService;
import com.zbkj.service.service.tl.TlModerationEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class TlContentReportServiceImpl implements TlContentReportService {
 @Resource private TlContentReportDao reportDao;
 @Resource private StallDao stallDao;
 @Resource private TlPostDao postDao;
 @Resource private TlCommentDao commentDao;
 @Resource private UserService userService;
 @Resource private TlModerationEventService moderationEventService;
 public Long submit(TlContentReportRequest request) {
  Integer userId=userService.getUserIdException(); String type=request.getTargetType().toLowerCase(Locale.ROOT); boolean visible=false;
  if("stall".equals(type)) visible=stallDao.selectCount(Wrappers.<Stall>lambdaQuery().eq(Stall::getId,request.getTargetId()).eq(Stall::getAuditStatus,1).eq(Stall::getIsDeleted,false))>0;
  else if("post".equals(type)) visible=postDao.selectCount(Wrappers.<TlPost>lambdaQuery().eq(TlPost::getId,request.getTargetId()).eq(TlPost::getAuditStatus,1).eq(TlPost::getIsDeleted,false))>0;
  else if("comment".equals(type)) visible=commentDao.selectCount(Wrappers.<TlComment>lambdaQuery().eq(TlComment::getId,request.getTargetId()).eq(TlComment::getAuditStatus,1).eq(TlComment::getIsDeleted,false))>0;
  else throw new IllegalArgumentException("targetType 只支持 stall、post、comment");
  if(!visible) throw new IllegalArgumentException("举报目标不存在或暂不可见");
  TlContentReport row=new TlContentReport().setTargetType(type).setTargetId(request.getTargetId()).setReporterUserId(userId).setReason(request.getReason().trim()).setStatus(0).setHideTarget(false).setCreateTime(new Date());
  reportDao.insert(row); return row.getId();
 }
 public PageInfo<TlContentReport> pending(PageParamRequest page) {
  Page<TlContentReport> paging=PageHelper.startPage(page.getPage(),page.getLimit());
  List<TlContentReport> rows=reportDao.selectList(Wrappers.<TlContentReport>lambdaQuery().eq(TlContentReport::getStatus,0).orderByAsc(TlContentReport::getCreateTime));
  attachTargetSummaries(rows);
  return CommonPage.copyPageInfo(paging,rows==null?Collections.emptyList():rows);
 }
 private void attachTargetSummaries(List<TlContentReport> rows) {
  if (rows == null || rows.isEmpty()) return;

  Set<Long> stallIds = new LinkedHashSet<>();
  Set<Long> postIds = new LinkedHashSet<>();
  Set<Long> commentIds = new LinkedHashSet<>();
  for (TlContentReport row : rows) {
   if (row.getTargetId() == null) continue;
   if ("stall".equals(row.getTargetType())) stallIds.add(row.getTargetId());
   else if ("post".equals(row.getTargetType())) postIds.add(row.getTargetId());
   else if ("comment".equals(row.getTargetType())) commentIds.add(row.getTargetId());
  }

  Map<Long, TlPost> posts = new HashMap<>();
  if (!postIds.isEmpty()) posts.putAll(postDao.selectBatchIds(postIds).stream()
          .collect(Collectors.toMap(TlPost::getId, Function.identity())));
  Map<Long, TlComment> comments = commentIds.isEmpty() ? Collections.emptyMap() :
          commentDao.selectBatchIds(commentIds).stream().collect(Collectors.toMap(TlComment::getId, Function.identity()));

  Set<Long> relatedPostIds = comments.values().stream().map(TlComment::getPostId).filter(id -> id != null)
          .collect(Collectors.toCollection(LinkedHashSet::new));
  if (!relatedPostIds.isEmpty()) {
   Map<Long, TlPost> relatedPosts = postDao.selectBatchIds(relatedPostIds).stream()
           .collect(Collectors.toMap(TlPost::getId, Function.identity()));
   posts.putAll(relatedPosts);
  }

  stallIds.addAll(posts.values().stream().map(TlPost::getStallId).filter(id -> id != null).collect(Collectors.toSet()));
  Map<Long, Stall> stalls = stallIds.isEmpty() ? Collections.emptyMap() :
          stallDao.selectBatchIds(stallIds).stream().collect(Collectors.toMap(Stall::getId, Function.identity()));

  for (TlContentReport row : rows) {
   if ("stall".equals(row.getTargetType())) {
    Stall stall = stalls.get(row.getTargetId());
    if (stall != null) row.setTargetSummary(stall.getName()).setTargetAddress(stall.getAddress());
   } else if ("post".equals(row.getTargetType())) {
    TlPost post = posts.get(row.getTargetId());
    if (post != null) row.setTargetSummary(post.getContent());
    attachRelatedStall(row, post, stalls);
   } else if ("comment".equals(row.getTargetType())) {
    TlComment comment = comments.get(row.getTargetId());
    if (comment != null) {
     row.setTargetSummary(comment.getContent());
     TlPost post = posts.get(comment.getPostId());
     if (post != null) row.setRelatedContent(post.getContent());
     attachRelatedStall(row, post, stalls);
    }
   }
  }
 }
 private void attachRelatedStall(TlContentReport row, TlPost post, Map<Long, Stall> stalls) {
  if (post == null || post.getStallId() == null) return;
  Stall stall = stalls.get(post.getStallId());
  if (stall != null) row.setRelatedStallName(stall.getName()).setRelatedStallAddress(stall.getAddress());
 }
 @Transactional(rollbackFor=Exception.class)
 public void handle(Long id,boolean resolve,boolean hideTarget,String note) {
  if (note != null && note.length() > 500) throw new IllegalArgumentException("处理备注最多500字");
  if (note != null) note = note.trim();
  TlContentReport row=reportDao.selectById(id); if(row==null||row.getStatus()!=0) throw new IllegalStateException("举报不存在或已处理");
  if (resolve && hideTarget) {
   int hidden = 0;
   if ("stall".equals(row.getTargetType())) hidden = stallDao.update(null, Wrappers.<Stall>lambdaUpdate()
           .eq(Stall::getId, row.getTargetId()).eq(Stall::getAuditStatus, 1).set(Stall::getAuditStatus, 3));
   else if ("post".equals(row.getTargetType())) hidden = postDao.update(null, Wrappers.<TlPost>lambdaUpdate()
           .eq(TlPost::getId, row.getTargetId()).eq(TlPost::getAuditStatus, 1).set(TlPost::getAuditStatus, 3));
   else hidden = commentDao.update(null, Wrappers.<TlComment>lambdaUpdate()
           .eq(TlComment::getId, row.getTargetId()).eq(TlComment::getAuditStatus, 1).set(TlComment::getAuditStatus, 3));
   if (hidden != 1) throw new IllegalStateException("举报目标已被其他操作处理");
   moderationEventService.recordAdmin(row.getTargetType(), row.getTargetId(), id, 1, 3, "hide", note);
  }
  Integer adminId = SecurityUtil.getLoginUserVo().getUser().getId();
  int changed = reportDao.update(null, Wrappers.<TlContentReport>lambdaUpdate().eq(TlContentReport::getId, id)
          .eq(TlContentReport::getStatus, 0).set(TlContentReport::getStatus, resolve ? 1 : 2)
          .set(TlContentReport::getHideTarget, resolve && hideTarget).set(TlContentReport::getHandledBy, adminId)
          .set(TlContentReport::getHandleNote, note).set(TlContentReport::getHandleTime, new Date()));
  if (changed != 1) throw new IllegalStateException("举报已由其他管理员处理");
  moderationEventService.recordAdmin("content_report", id, row.getTargetId(), 0, resolve ? 1 : 2,
          resolve ? "resolve" : "dismiss", note);
 }
}
