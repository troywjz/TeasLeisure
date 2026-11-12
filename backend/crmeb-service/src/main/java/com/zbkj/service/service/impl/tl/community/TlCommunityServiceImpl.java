package com.zbkj.service.service.impl.tl.community;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.community.TlComment;
import com.zbkj.common.model.tl.community.TlPost;
import com.zbkj.common.model.tl.TlModerationEvent;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.community.CommentCreateRequest;
import com.zbkj.common.request.tl.community.PostCreateRequest;
import com.zbkj.common.response.tl.community.TlPostResponse;
import com.zbkj.common.response.tl.community.TlCommentResponse;
import com.zbkj.common.response.tl.community.TlPostLikeCountResponse;
import com.zbkj.common.response.tl.community.TlMyPostResponse;
import com.zbkj.common.response.tl.community.TlMyCommentResponse;
import com.zbkj.common.model.tl.Stall;
import com.zbkj.common.model.user.User;
import com.zbkj.service.dao.tl.StallDao;
import com.zbkj.service.dao.UserDao;
import com.zbkj.service.dao.tl.community.TlCommentDao;
import com.zbkj.service.dao.tl.community.TlPostDao;
import com.zbkj.service.dao.tl.community.TlReactionDao;
import com.zbkj.service.dao.tl.TlModerationEventDao;
import com.zbkj.service.service.UserService;
import com.zbkj.service.service.tl.TlModerationEventService;
import com.zbkj.service.service.tl.community.TlCommunityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TlCommunityServiceImpl extends ServiceImpl<TlPostDao, TlPost> implements TlCommunityService {
    @Resource private TlPostDao postDao;
    @Resource private TlCommentDao commentDao;
    @Resource private UserService userService;
    @Resource private UserDao userDao;
    @Resource private TlReactionDao reactionDao;
    @Resource private StallDao stallDao;
    @Resource private TlModerationEventDao moderationEventDao;
    @Resource private TlModerationEventService moderationEventService;

    @Override
    public Long create(PostCreateRequest request) {
        Integer userId = userService.getUserIdException();
        if (stallDao.selectCount(Wrappers.<Stall>lambdaQuery()
                .eq(Stall::getId, request.getStallId()).eq(Stall::getOwnerUserId, userId)
                .eq(Stall::getAuditStatus, 1).eq(Stall::getIsDeleted, false)) == 0) {
            throw new IllegalArgumentException("只能为本人已认领且已发布的摊位发布动态");
        }
        TlPost post = new TlPost().setStallId(request.getStallId()).setAuthorId(userId)
                .setContent(request.getContent().trim()).setImageUrls(request.getImageUrls())
                .setAuditStatus(0).setIsDeleted(false);
        save(post);
        return post.getId();
    }

    @Override
    public PageInfo<TlPost> pendingPosts(PageParamRequest request) {
        Page<TlPost> page = PageHelper.startPage(request.getPage(), request.getLimit());
        List<TlPost> rows = postDao.selectList(Wrappers.<TlPost>lambdaQuery()
                .eq(TlPost::getAuditStatus, 0).eq(TlPost::getIsDeleted, false)
                .orderByAsc(TlPost::getCreateTime));
        return CommonPage.copyPageInfo(page, rows == null ? Collections.emptyList() : rows);
    }

    @Override
    public PageInfo<TlComment> pendingComments(PageParamRequest request) {
        Page<TlComment> page = PageHelper.startPage(request.getPage(), request.getLimit());
        List<TlComment> rows = commentDao.selectList(Wrappers.<TlComment>lambdaQuery()
                .eq(TlComment::getAuditStatus, 0).eq(TlComment::getIsDeleted, false)
                .orderByAsc(TlComment::getCreateTime));
        return CommonPage.copyPageInfo(page, rows == null ? Collections.emptyList() : rows);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reviewPost(Long id, boolean approve, String note) {
        int changed = postDao.update(null, Wrappers.<TlPost>lambdaUpdate().eq(TlPost::getId, id)
                .eq(TlPost::getAuditStatus, 0).eq(TlPost::getIsDeleted, false)
                .set(TlPost::getAuditStatus, approve ? 1 : 2));
        if (changed != 1) throw new IllegalArgumentException("动态不存在或已完成审核");
        moderationEventService.recordAdmin("post", id, null, 0, approve ? 1 : 2,
                approve ? "approve" : "reject", note);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reviewComment(Long id, boolean approve, String note) {
        int changed = commentDao.update(null, Wrappers.<TlComment>lambdaUpdate().eq(TlComment::getId, id)
                .eq(TlComment::getAuditStatus, 0).eq(TlComment::getIsDeleted, false)
                .set(TlComment::getAuditStatus, approve ? 1 : 2));
        if (changed != 1) throw new IllegalArgumentException("评论不存在或已完成审核");
        moderationEventService.recordAdmin("comment", id, null, 0, approve ? 1 : 2,
                approve ? "approve" : "reject", note);
    }

    @Override
    public PageInfo<TlPostResponse> list(Long stallId, Integer categoryId, PageParamRequest request) {
        Page<TlPost> page = PageHelper.startPage(request.getPage(), request.getLimit());
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<TlPost> query = Wrappers.<TlPost>lambdaQuery()
                .eq(TlPost::getAuditStatus, 1).eq(TlPost::getIsDeleted, false)
                .apply("stall_id IN (SELECT id FROM eb_tl_stall WHERE audit_status = 1 AND is_deleted = 0)")
                .eq(stallId != null, TlPost::getStallId, stallId);
        if (categoryId != null) {
            query.apply("stall_id IN (SELECT id FROM eb_tl_stall WHERE category_id = {0} AND audit_status = 1 AND is_deleted = 0)", categoryId);
        }
        List<TlPost> posts = postDao.selectList(query.orderByDesc(TlPost::getCreateTime).orderByDesc(TlPost::getId));
        if (posts == null || posts.isEmpty()) return CommonPage.copyPageInfo(page, Collections.emptyList());

        return CommonPage.copyPageInfo(page, toPublicResponses(posts));
    }

    @Override
    public PageInfo<TlMyPostResponse> myPosts(PageParamRequest request) {
        Integer authorId = userService.getUserIdException();
        Page<TlPost> page = PageHelper.startPage(request.getPage(), request.getLimit());
        List<TlPost> posts = postDao.selectList(Wrappers.<TlPost>lambdaQuery()
                .eq(TlPost::getAuthorId, authorId)
                .eq(TlPost::getIsDeleted, false)
                .orderByDesc(TlPost::getCreateTime).orderByDesc(TlPost::getId));
        if (posts == null || posts.isEmpty()) return CommonPage.copyPageInfo(page, Collections.emptyList());

        List<Long> stallIds = posts.stream().map(TlPost::getStallId).filter(java.util.Objects::nonNull).distinct().collect(Collectors.toList());
        Map<Long, Stall> stalls = stallIds.isEmpty() ? Collections.emptyMap() : stallDao.selectBatchIds(stallIds).stream()
                .collect(Collectors.toMap(Stall::getId, stall -> stall, (first, ignored) -> first));
        List<Long> postIds = posts.stream().map(TlPost::getId).collect(Collectors.toList());
        List<TlModerationEvent> events = moderationEventDao.selectList(Wrappers.<TlModerationEvent>lambdaQuery()
                .eq(TlModerationEvent::getTargetType, "post")
                .in(TlModerationEvent::getTargetId, postIds)
                .orderByDesc(TlModerationEvent::getCreateTime).orderByDesc(TlModerationEvent::getId));
        Map<Long, String> latestReason = new HashMap<>();
        for (TlModerationEvent event : events) {
            if (!latestReason.containsKey(event.getTargetId())) latestReason.put(event.getTargetId(), event.getReason());
        }

        List<TlMyPostResponse> result = new ArrayList<>(posts.size());
        for (TlPost post : posts) {
            TlMyPostResponse response = new TlMyPostResponse()
                    .setId(post.getId())
                    .setStallId(post.getStallId())
                    .setContent(post.getContent())
                    .setImageUrls(post.getImageUrls())
                    .setAuditStatus(post.getAuditStatus())
                    .setPubliclyVisible(isPublicPost(post, stalls))
                    .setCreateTime(post.getCreateTime())
                    .setAuditNote(latestReason.get(post.getId()));
            Stall stall = stalls.get(post.getStallId());
            if (stall != null && !Boolean.TRUE.equals(stall.getIsDeleted())) {
                response.setStallName(stall.getName())
                        .setStallAddress(stall.getAddress())
                        .setStallCoverImage(stall.getCoverImage());
            }
            result.add(response);
        }
        return CommonPage.copyPageInfo(page, result);
    }

    @Override
    public PageInfo<TlMyCommentResponse> myComments(PageParamRequest request) {
        Integer authorId = userService.getUserIdException();
        Page<TlComment> page = PageHelper.startPage(request.getPage(), request.getLimit());
        List<TlComment> comments = commentDao.selectList(Wrappers.<TlComment>lambdaQuery()
                .eq(TlComment::getAuthorId, authorId)
                .eq(TlComment::getIsDeleted, false)
                .orderByDesc(TlComment::getCreateTime).orderByDesc(TlComment::getId));
        if (comments == null || comments.isEmpty()) return CommonPage.copyPageInfo(page, Collections.emptyList());

        List<Long> postIds = comments.stream().map(TlComment::getPostId).filter(java.util.Objects::nonNull).distinct().collect(Collectors.toList());
        Map<Long, TlPost> posts = postIds.isEmpty() ? Collections.emptyMap() : postDao.selectBatchIds(postIds).stream()
                .collect(Collectors.toMap(TlPost::getId, post -> post, (first, ignored) -> first));
        Set<Long> stallIds = posts.values().stream()
                .filter(post -> Integer.valueOf(1).equals(post.getAuditStatus()) && !Boolean.TRUE.equals(post.getIsDeleted()))
                .map(TlPost::getStallId).filter(java.util.Objects::nonNull).collect(Collectors.toSet());
        Map<Long, Stall> stalls = stallIds.isEmpty() ? Collections.emptyMap() : stallDao.selectBatchIds(stallIds).stream()
                .collect(Collectors.toMap(Stall::getId, stall -> stall, (first, ignored) -> first));

        List<Long> commentIds = comments.stream().map(TlComment::getId).collect(Collectors.toList());
        List<TlModerationEvent> events = moderationEventDao.selectList(Wrappers.<TlModerationEvent>lambdaQuery()
                .eq(TlModerationEvent::getTargetType, "comment")
                .in(TlModerationEvent::getTargetId, commentIds)
                .orderByDesc(TlModerationEvent::getCreateTime).orderByDesc(TlModerationEvent::getId));
        Map<Long, String> latestReason = new HashMap<>();
        for (TlModerationEvent event : events) {
            if (!latestReason.containsKey(event.getTargetId())) latestReason.put(event.getTargetId(), event.getReason());
        }

        List<TlMyCommentResponse> result = new ArrayList<>(comments.size());
        for (TlComment comment : comments) {
            TlMyCommentResponse response = new TlMyCommentResponse()
                    .setId(comment.getId())
                    .setPostId(comment.getPostId())
                    .setParentId(comment.getParentId())
                    .setContent(comment.getContent())
                    .setAuditStatus(comment.getAuditStatus())
                    .setCreateTime(comment.getCreateTime())
                    .setAuditNote(latestReason.get(comment.getId()));
            TlPost post = posts.get(comment.getPostId());
            boolean postPublished = post != null && Integer.valueOf(1).equals(post.getAuditStatus())
                    && !Boolean.TRUE.equals(post.getIsDeleted());
            response.setPostPublished(postPublished);
            response.setPostPubliclyVisible(isPublicPost(post, stalls));
            if (postPublished) {
                response.setPostContent(post.getContent()).setPostImageUrls(post.getImageUrls());
                Stall stall = stalls.get(post.getStallId());
                if (response.getPostPubliclyVisible() && stall != null) {
                    response.setStallName(stall.getName())
                            .setStallAddress(stall.getAddress())
                            .setStallCoverImage(stall.getCoverImage());
                }
            }
            result.add(response);
        }
        return CommonPage.copyPageInfo(page, result);
    }

    @Override
    public TlPostResponse detail(Long id) {
        TlPost post = postDao.selectOne(Wrappers.<TlPost>lambdaQuery().eq(TlPost::getId, id)
                .eq(TlPost::getAuditStatus, 1).eq(TlPost::getIsDeleted, false)
                .isNotNull(TlPost::getStallId));
        if (post == null || !isPublicStall(post.getStallId())) return null;
        return toPublicResponses(Collections.singletonList(post)).get(0);
    }

    private List<TlPostResponse> toPublicResponses(List<TlPost> posts) {
        List<Long> postIds = posts.stream().map(TlPost::getId).collect(Collectors.toList());
        List<Integer> authorIds = posts.stream().map(TlPost::getAuthorId).distinct().collect(Collectors.toList());
        Map<Integer, User> users = userDao.selectBatchIds(authorIds).stream()
                .collect(Collectors.toMap(User::getUid, user -> user, (left, right) -> left));
        List<Long> stallIds = posts.stream().map(TlPost::getStallId).filter(java.util.Objects::nonNull).distinct().collect(Collectors.toList());
        Map<Long, Stall> stalls = stallIds.isEmpty() ? Collections.emptyMap() : stallDao.selectBatchIds(stallIds).stream()
                .collect(Collectors.toMap(Stall::getId, stall -> stall, (left, right) -> left));
        Map<Long, Integer> likeCounts = reactionDao.countLikesByPostIds(postIds).stream()
                .collect(Collectors.toMap(TlPostLikeCountResponse::getPostId, TlPostLikeCountResponse::getLikeCount));
        Integer viewerId = userService.getUserId();
        Set<Long> likedPostIds = viewerId == null ? Collections.emptySet() : reactionDao.selectList(Wrappers.<com.zbkj.common.model.tl.community.TlReaction>lambdaQuery()
                .eq(com.zbkj.common.model.tl.community.TlReaction::getUserId, viewerId)
                .eq(com.zbkj.common.model.tl.community.TlReaction::getReactionType, "like")
                .in(com.zbkj.common.model.tl.community.TlReaction::getPostId, postIds)).stream()
                .map(com.zbkj.common.model.tl.community.TlReaction::getPostId).collect(Collectors.toSet());
        List<TlPostResponse> responses = new java.util.ArrayList<>(posts.size());
        for (TlPost post : posts) {
            TlPostResponse response = new TlPostResponse();
            response.setId(post.getId());
            response.setStallId(post.getStallId());
            response.setContent(post.getContent());
            response.setImageUrls(post.getImageUrls());
            response.setCreateTime(post.getCreateTime());
            Stall linkedStall = stalls.get(post.getStallId());
            if (linkedStall != null) {
                response.setStallName(linkedStall.getName());
                response.setStallAddress(linkedStall.getAddress());
                response.setStallCoverImage(linkedStall.getCoverImage());
            }
            User author = users.get(post.getAuthorId());
            response.setAuthorName(author == null ? "茶余用户" : author.getNickname());
            response.setAuthorAvatar(author == null ? null : author.getAvatar());
            response.setLikeCount(likeCounts.getOrDefault(post.getId(), 0));
            response.setLiked(likedPostIds.contains(post.getId()));
            response.setMine(viewerId != null && viewerId.equals(post.getAuthorId()));
            responses.add(response);
        }
        return responses;
    }

    @Override
    public Long comment(Long postId, CommentCreateRequest request) {
        if (!isPublishedPost(postId)) throw new IllegalArgumentException("动态不存在或暂不可见");
        Integer userId = userService.getUserIdException();
        Long parentId = request.getParentId();
        if (parentId != null) {
            TlComment parent = commentDao.selectOne(Wrappers.<TlComment>lambdaQuery()
                    .eq(TlComment::getId, parentId).eq(TlComment::getPostId, postId)
                    .eq(TlComment::getAuditStatus, 1).eq(TlComment::getIsDeleted, false));
            if (parent == null) throw new IllegalArgumentException("回复目标不存在或暂不可见");
        }
        TlComment comment = new TlComment().setPostId(postId).setAuthorId(userId)
                .setParentId(parentId).setContent(request.getContent().trim())
                .setAuditStatus(0).setIsDeleted(false);
        commentDao.insert(comment);
        return comment.getId();
    }

    @Override
    public PageInfo<TlCommentResponse> comments(Long postId, PageParamRequest request) {
        if (!isPublishedPost(postId)) throw new IllegalArgumentException("动态不存在或暂不可见");
        Page<TlComment> page = PageHelper.startPage(request.getPage(), request.getLimit());
        List<TlComment> rows = commentDao.selectList(Wrappers.<TlComment>lambdaQuery()
                .eq(TlComment::getPostId, postId).eq(TlComment::getAuditStatus, 1)
                .eq(TlComment::getIsDeleted, false).orderByAsc(TlComment::getCreateTime));
        List<TlComment> commentRows = rows == null ? Collections.emptyList() : rows;
        Set<Integer> authorIds = commentRows.stream().map(TlComment::getAuthorId).filter(java.util.Objects::nonNull).collect(Collectors.toSet());
        Map<Integer, User> authors = authorIds.isEmpty() ? Collections.emptyMap() : userDao.selectBatchIds(authorIds).stream()
                .collect(Collectors.toMap(User::getUid, user -> user, (left, right) -> left));
        Integer viewerId = userService.getUserId();
        List<TlCommentResponse> responses = new java.util.ArrayList<>(commentRows.size());
        for (TlComment comment : commentRows) {
            User author = authors.get(comment.getAuthorId());
            TlCommentResponse response = new TlCommentResponse();
            response.setId(comment.getId());
            response.setPostId(comment.getPostId());
            response.setParentId(comment.getParentId());
            response.setAuthorName(author == null || author.getNickname() == null ? "茶余用户" : author.getNickname());
            response.setAuthorAvatar(author == null ? null : author.getAvatar());
            response.setContent(comment.getContent());
            response.setCreateTime(comment.getCreateTime());
            response.setMine(viewerId != null && viewerId.equals(comment.getAuthorId()));
            responses.add(response);
        }
        return CommonPage.copyPageInfo(page, responses);
    }

    @Override
    public void deletePost(Long id) {
        Integer userId = userService.getUserIdException();
        if (postDao.update(null, Wrappers.<TlPost>lambdaUpdate().eq(TlPost::getId, id)
                .eq(TlPost::getAuthorId, userId).eq(TlPost::getIsDeleted, false)
                .set(TlPost::getIsDeleted, true)) != 1) throw new IllegalArgumentException("动态不存在或无权删除");
    }

    @Override
    public void deleteComment(Long id) {
        Integer userId = userService.getUserIdException();
        if (commentDao.update(null, Wrappers.<TlComment>lambdaUpdate().eq(TlComment::getId, id)
                .eq(TlComment::getAuthorId, userId).eq(TlComment::getIsDeleted, false)
                .set(TlComment::getIsDeleted, true)) != 1) throw new IllegalArgumentException("评论不存在或无权删除");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void react(Long postId, boolean active) {
        if (!isPublishedPost(postId)) throw new IllegalArgumentException("动态不存在或暂不可见");
        Integer userId = userService.getUserIdException();
        if (active) {
            Integer count = countReaction(postId, userId);
            if (count == 0) {
                com.zbkj.common.model.tl.community.TlReaction reaction = new com.zbkj.common.model.tl.community.TlReaction()
                        .setPostId(postId).setUserId(userId).setReactionType("like").setCreateTime(new Date());
                try { reactionDao.insert(reaction); } catch (org.springframework.dao.DuplicateKeyException ignored) { }
            }
        } else {
            reactionDao.delete(Wrappers.<com.zbkj.common.model.tl.community.TlReaction>lambdaQuery()
                    .eq(com.zbkj.common.model.tl.community.TlReaction::getPostId, postId)
                    .eq(com.zbkj.common.model.tl.community.TlReaction::getUserId, userId)
                    .eq(com.zbkj.common.model.tl.community.TlReaction::getReactionType, "like"));
        }
    }

    private Integer countReaction(Long postId, Integer userId) {
        return Math.toIntExact(reactionDao.selectCount(Wrappers.<com.zbkj.common.model.tl.community.TlReaction>lambdaQuery()
                .eq(com.zbkj.common.model.tl.community.TlReaction::getPostId, postId)
                .eq(com.zbkj.common.model.tl.community.TlReaction::getUserId, userId)
                .eq(com.zbkj.common.model.tl.community.TlReaction::getReactionType, "like")));
    }

    private boolean isPublishedPost(Long id) {
        TlPost post = postDao.selectOne(Wrappers.<TlPost>lambdaQuery().eq(TlPost::getId, id)
                .eq(TlPost::getAuditStatus, 1).eq(TlPost::getIsDeleted, false)
                .isNotNull(TlPost::getStallId));
        return post != null && isPublicStall(post.getStallId());
    }

    private boolean isPublicPost(TlPost post, Map<Long, Stall> stalls) {
        if (post == null || !Integer.valueOf(1).equals(post.getAuditStatus())
                || Boolean.TRUE.equals(post.getIsDeleted()) || post.getStallId() == null) return false;
        Stall stall = stalls.get(post.getStallId());
        return stall != null && Integer.valueOf(1).equals(stall.getAuditStatus())
                && !Boolean.TRUE.equals(stall.getIsDeleted());
    }

    private boolean isPublicStall(Long stallId) {
        return stallId != null && stallDao.selectCount(Wrappers.<Stall>lambdaQuery().eq(Stall::getId, stallId)
                .eq(Stall::getAuditStatus, 1).eq(Stall::getIsDeleted, false)) > 0;
    }
}
