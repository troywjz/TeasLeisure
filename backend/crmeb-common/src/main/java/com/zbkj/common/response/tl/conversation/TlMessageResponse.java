package com.zbkj.common.response.tl.conversation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/** Safe message view for an authenticated conversation member. */
@Data
@Accessors(chain = true)
public class TlMessageResponse {
    private Long id;
    private String content;
    private Date createTime;
    private Boolean mine;
}
