package com.zbkj.common.request.onepass;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * @Auther: 大粽子
 * @Date: 2023/8/28 09:38
 * @Description: 一号通 商家寄件 回调对象
 */
@Data
public class OnePassShipmentCallBackRequest {

    @Schema(description = "回调类型：order_success=下单成功回调｜order_take=取件回调｜order_cancel=用户主动取消回调｜order_fail=下单失败回调｜order_receipt=快递签收回调")
    public String type;

    @Schema(description = "回调数据")
    public String data;
}
