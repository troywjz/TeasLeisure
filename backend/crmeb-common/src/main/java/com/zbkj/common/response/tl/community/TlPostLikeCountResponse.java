package com.zbkj.common.response.tl.community;

import lombok.Data;

@Data
public class TlPostLikeCountResponse {
    private Long postId;
    private Integer likeCount;
}
