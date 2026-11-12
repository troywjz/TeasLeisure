package com.zbkj.common.request;

import com.zbkj.common.annotation.StringContains;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 套餐购买请求对象
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
@Schema(name = "MealCodeRequest对象", description = "套餐购买请求对象")
public class MealCodeRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "套餐ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "套餐ID不能为空")
    private Integer mealId;

    @Schema(description = "套餐金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "套餐金额不能为空")
    private BigDecimal price;

    @Schema(description = "套餐量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "套餐量不能为空")
    private Integer num;

    @Schema(description = "套餐类型:sms,短信;copy,产品复制;expr_query,物流查询;expr_dump,电子面单", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "套餐类型不能为空")
    @StringContains(limitValues = {"sms","copy","expr_query","expr_dump"}, message = "未知的套餐类型")
    private String type;

    @Schema(description = "支付类型 weixin：微信支付/alipay：支付宝支付", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "支付类型不能为空")
    @StringContains(limitValues = {"weixin","alipay"}, message = "未知的支付类型")
    private String payType;

}
