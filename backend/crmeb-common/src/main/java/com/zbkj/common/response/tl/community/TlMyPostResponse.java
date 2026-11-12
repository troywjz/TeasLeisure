package com.zbkj.common.response.tl.community;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/** Authenticated author's safe post review history. */
@Data
@Accessors(chain = true)
public class TlMyPostResponse {
    private Long id;
    private Long stallId;
    private String stallName;
    private String stallAddress;
    private String stallCoverImage;
    private String content;
    private String imageUrls;
    private Integer auditStatus;
    /** Whether the post currently qualifies for public list/detail access. */
    private Boolean publiclyVisible;
    private Date createTime;
    private String auditNote;
}
