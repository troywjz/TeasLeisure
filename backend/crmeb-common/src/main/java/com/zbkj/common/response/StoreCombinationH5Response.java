package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  拼团商品移动端对象
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
@Schema(name = "StoreCombinationH5Response对象", description = "拼团商品移动端对象")
public class StoreCombinationH5Response implements Serializable {

    private static final long serialVersionUID = -885733985825623484L;

    @Schema(description = "拼团商品ID")
    private Integer id;

    @Schema(description = "商品id")
    private Integer productId;

    @Schema(description = "推荐图")
    private String image;

    @Schema(description = "活动标题")
    private String title;

    @Schema(description = "参团人数")
    private Integer people;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "原价")
    private BigDecimal otPrice;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "商品详情")
    private String content;

    @Schema(description = "收藏标识")
    private boolean userCollect;
}
