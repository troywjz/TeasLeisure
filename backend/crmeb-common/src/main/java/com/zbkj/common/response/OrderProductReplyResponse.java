package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 评价商品页响应对象
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
@Schema(name = "OrderProductReplyResponse对象", description = "评价商品页响应对象")
public class OrderProductReplyResponse implements Serializable {

    private static final long serialVersionUID = -1926585407216207845L;

    @Schema(description = "购买数量")
    private Integer cartNum;

    @Schema(description = "价格")
    private BigDecimal truePrice;

    @Schema(description = "商品名称")
    private String storeName;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "商品编号")
    private Integer productId;

    @Schema(description = "规格sku")
    private String sku;

}
