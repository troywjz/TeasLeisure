package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "StoreOrderTabsNumRequest", description = "订单表头请求对象")
public class StoreOrderTabsNumRequest extends UserCommonSearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "创建时间区间")
    private String dateLimit;

    @Schema(description = "订单类型：0普通订单，1-视频号订单, 2-全部订单")
    @NotNull(message = "订单类型不能为空")
    @Range(min = 0, max = 2, message = "未知的订单类型")
    private Integer type;

    @Schema(description = "物流单号")
    private String deliveryId;
}
