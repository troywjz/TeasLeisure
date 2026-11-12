package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 申请退款订单响应对象
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
@Schema(name = "ApplyRefundOrderInfoResponse对象", description = "申请退款订单响应对象")
public class ApplyRefundOrderInfoResponse implements Serializable {

    private static final long serialVersionUID = 1387727608277207652L;

    @Schema(description = "订单id")
    private Integer id;

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "支付状态")
    private Boolean paid;

    @Schema(description = "支付金额")
    private BigDecimal payPrice;

    @Schema(description = "订单商品总数")
    private Integer totalNum;

    @Schema(description = "订单详情对象列表")
    private List<OrderInfoResponse> orderInfoList;

}
