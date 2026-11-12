package com.zbkj.common.model.record;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商城交易日记录表
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
@TableName("eb_trading_day_record")
@Schema(name = "TradingDayRecord对象", description = "商城交易日记录表")
public class TradingDayRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "日期")
    private String date;

    @Schema(description = "订单数量")
    private Integer productOrderNum;

    @Schema(description = "订单支付数量")
    private Integer productOrderPayNum;

    @Schema(description = "订单支付金额")
    private BigDecimal productOrderPayFee;

    @Schema(description = "订单退款数量")
    private Integer productOrderRefundNum;

    @Schema(description = "订单退款金额")
    private BigDecimal productOrderRefundFee;

    @Schema(description = "充值订单数量")
    private Integer rechargeOrderNum;

    @Schema(description = "充值订单金额")
    private BigDecimal rechargeOrderFee;

    @Schema(description = "余额支付金额")
    private BigDecimal balancePayFee;

    @Schema(description = "支付佣金金额（用户确认到账佣金）")
    private BigDecimal brokerageFee;


}
