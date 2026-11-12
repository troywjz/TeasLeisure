package com.zbkj.common.request.onepass;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * @Auther: 大粽子
 * @Date: 2023/8/26 15:40
 * @Description: 一号通 取消商家寄件
 */
@Data
public class OnePassShipmentCancelOrderRequest {

    @Schema(description = "任务ID，在[创建商家寄件订单]接口中获得", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "任务ID 不能为空")
    public String taskId;

    @Schema(description = "订单ID，在[创建商家寄件订单]接口中获得", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单ID 不能为空")
    public String orderId;

    @Schema(description = "取消理由", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "取消理由 不能为空")
    public String cancelMsg;
}
