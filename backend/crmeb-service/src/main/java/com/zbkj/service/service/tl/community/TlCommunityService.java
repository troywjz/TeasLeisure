package com.zbkj.service.service.tl.community;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.community.TlComment;
import com.zbkj.common.model.tl.community.TlPost;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.community.CommentCreateRequest;
import com.zbkj.common.request.tl.community.PostCreateRequest;
import com.zbkj.common.response.tl.community.TlPostResponse;
import com.zbkj.common.response.tl.community.TlCommentResponse;
import com.zbkj.common.response.tl.community.TlMyPostResponse;
import com.zbkj.common.response.tl.community.TlMyCommentResponse;

public interface TlCommunityService {
    Long create(PostCreateRequest request);
    PageInfo<TlPostResponse> list(Long stallId, Integer categoryId, PageParamRequest page);
    PageInfo<TlMyPostResponse> myPosts(PageParamRequest page);
    PageInfo<TlMyCommentResponse> myComments(PageParamRequest page);
    TlPostResponse detail(Long id);
    Long comment(Long postId, CommentCreateRequest request);
    PageInfo<TlCommentResponse> comments(Long postId, PageParamRequest page);
    PageInfo<TlPost> pendingPosts(PageParamRequest page);
    PageInfo<TlComment> pendingComments(PageParamRequest page);
    void reviewPost(Long id, boolean approve, String note);
    void reviewComment(Long id, boolean approve, String note);
    void deletePost(Long id);
    void deleteComment(Long id);
    void react(Long postId, boolean active);
}
