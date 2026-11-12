package com.zbkj.common.response.tl.community;

import lombok.Data;

import java.util.Date;

/** Public comment fields. Database and account identifiers are omitted. */
@Data
public class TlCommentResponse {
    private Long id;
    private Long postId;
    private Long parentId;
    private String authorName;
    private String authorAvatar;
    private String content;
    private Date createTime;
    /** True only for the currently authenticated author. */
    private Boolean mine;
}
