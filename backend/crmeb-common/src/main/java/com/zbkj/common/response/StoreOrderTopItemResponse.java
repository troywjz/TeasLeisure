package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单九宫格数据
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
@Schema(name = "StoreOrderTopItemResponse对象", description = "订单九宫格数据")
public class StoreOrderTopItemResponse implements Serializable {

    private static final long serialVersionUID = -7583407020447771557L;

    @Schema(description = "订单数量")
    private Integer count;

    @Schema(description = "订单金额")
    private BigDecimal amount;

    @Schema(description = "微信支付金额")
    private BigDecimal weChatAmount;

    @Schema(description = "余额支付")
    private BigDecimal yueAmount;
}
