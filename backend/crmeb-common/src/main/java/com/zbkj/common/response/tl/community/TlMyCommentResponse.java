package com.zbkj.common.response.tl.community;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/** Authenticated comment author's safe review history. */
@Data
@Accessors(chain = true)
public class TlMyCommentResponse {
    private Long id;
    private Long postId;
    private Long parentId;
    private String content;
    private Integer auditStatus;
    private Date createTime;
    private String auditNote;
    private Boolean postPublished;
    /** Whether the parent post and its linked stall are currently public. */
    private Boolean postPubliclyVisible;
    private String postContent;
    private String postImageUrls;
    private String stallName;
    private String stallAddress;
    private String stallCoverImage;
}
