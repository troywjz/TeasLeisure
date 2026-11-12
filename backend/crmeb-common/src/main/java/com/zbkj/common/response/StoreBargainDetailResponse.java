package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 砍价商品详情响应对象（列表）
 * +----------------------------------------------------------------------
 * | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 * +----------------------------------------------------------------------
 * | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 * +----------------------------------------------------------------------
 * | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 * +----------------------------------------------------------------------
 * | Author: CRMEB Team <admin@crmeb.com>
 * +----------------------------------------------------------------------
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "StoreBargainDetailResponse对象", description = "砍价商品详情响应对象（列表）")
public class StoreBargainDetailResponse implements Serializable {

    private static final long serialVersionUID = 969438774401700566L;

    @Schema(description = "砍价商品ID")
    private Integer id;

    @Schema(description = "关联商品ID")
    private Integer productId;

    @Schema(description = "砍价活动名称")
    private String title;

    @Schema(description = "砍价活动图片")
    private String image;

    @Schema(description = "砍价开启时间")
    private Long startTime;

    @Schema(description = "砍价结束时间")
    private Long stopTime;

    @Schema(description = "砍价商品最低价")
    private BigDecimal minPrice;

    @Schema(description = "限购总数")
    private Integer quota;
}
