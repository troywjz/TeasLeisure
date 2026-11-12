package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 交易统计数据对象
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
@Schema(name = "TradeDataResponse对象", description = "交易统计数据对象")
public class TradeDataResponse implements Serializable {

    private static final long serialVersionUID = -6332062115310922579L;

    @Schema(description = "今日订单数量")
    private Integer todayOrderNum;

    @Schema(description = "昨日订单数量")
    private Integer yesterdayOrderNum;

    @Schema(description = "昨日交易数量日环比")
    private String yesterdayOrderNumRatio;

    @Schema(description = "昨日交易金额")
    private BigDecimal yesterdayOrderSales;

    @Schema(description = "昨日交易金额日环比")
    private String yesterdayOrderSalesRatio;

    @Schema(description = "本月交易数量")
    private Integer monthOrderNum;

    @Schema(description = "本月交易数量月环比")
    private String monthOrderNumRatio;

    @Schema(description = "本月交易金额")
    private BigDecimal monthOrderSales;

    @Schema(description = "本月交易金额月环比")
    private String monthOrderSalesRatio;

}
