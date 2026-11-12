package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 用户充值退款请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserRechargeRefundRequest对象", description = "用户充值退款")
public class UserRechargeRefundRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "充值记录ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "充值记录ID不能为空")
    @Min(value = 1, message = "充值记录ID必须大于0")
    private Integer id;

    @Schema(description = "充值订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "充值订单号不能为空")
    private String orderId;

    @Schema(description = "退款类型：1-仅本金，2-本金和赠送金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "退款类型不能为空")
    @Range(min = 1, max = 2, message = "退款类型不正确")
    private Integer type;
}
