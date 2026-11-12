package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 计算订单价格响应对象
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
@Schema(name = "ComputedOrderPriceResponse对象", description = "计算订单价格响应对象")
public class ComputedOrderPriceResponse implements Serializable {

    private static final long serialVersionUID = 7282892323898493847L;

    @Schema(description = "优惠券优惠金额")
    private BigDecimal couponFee;

    @Schema(description = "积分抵扣金额")
    private BigDecimal deductionPrice;

    @Schema(description = "运费金额")
    private BigDecimal freightFee;

    @Schema(description = "实际支付金额")
    private BigDecimal payFee;

    @Schema(description = "商品总金额")
    private BigDecimal proTotalFee;

    @Schema(description = "剩余积分")
    private Integer surplusIntegral;

    @Schema(description = "是否使用积分")
    private Boolean useIntegral;

    @Schema(description = "使用的积分")
    private Integer usedIntegral;
}
