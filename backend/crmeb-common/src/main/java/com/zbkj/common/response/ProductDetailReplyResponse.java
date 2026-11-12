package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品详情评论响应对象
 *  +----------------------------------------------------------------------
 *  | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 *  +----------------------------------------------------------------------
 *  | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 *  +----------------------------------------------------------------------
 *  | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 *  +----------------------------------------------------------------------
 *  | Author: CRMEB Team <admin@crmeb.com>
 *  +----------------------------------------------------------------------
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ProductDetailReplyResponse对象", description = "商品详情评论响应对象")
public class ProductDetailReplyResponse implements Serializable {

    private static final long serialVersionUID = 8822745472328151094L;

    @Schema(description = "评论总数")
    private Integer sumCount;

    @Schema(description = "好评率")
    private String replyChance;

    @Schema(description = "最后一条评论信息")
    private ProductReplyResponse productReply;
}
