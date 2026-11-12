package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 交易概览数据对象
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
@Schema(name = "TradingDataResponse对象", description = "交易概览数据对象")
public class TrandeTrendDateResponse implements Serializable {

    private static final long serialVersionUID = -6332062115310922579L;

    @Schema(description = "日期")
    private String date;

    @Schema(description = "营业额")
    private BigDecimal turnover;

    @Schema(description = "商品支付金额")
    private BigDecimal proPayAmount;

    @Schema(description = "充值金额")
    private BigDecimal rechargeAmount;

    @Schema(description = "支出金额")
    private BigDecimal payoutAmount;

}
