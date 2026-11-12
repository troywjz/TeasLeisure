package com.zbkj.common.response.tl.community;

import lombok.Data;
import java.util.Date;

/** Public post DTO. Moderation and database ownership fields stay private. */
@Data
public class TlPostResponse {
    private Long id;
    private Long stallId;
    private String stallName;
    private String stallAddress;
    private String stallCoverImage;
    private String authorName;
    private String authorAvatar;
    private String content;
    private String imageUrls;
    private Date createTime;
    private Integer likeCount;
    private Boolean liked;
    /** True only for the currently authenticated author. */
    private Boolean mine;
}
