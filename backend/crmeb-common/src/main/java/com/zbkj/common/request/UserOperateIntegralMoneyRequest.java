package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 资金操作
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
@Schema(name = "资金操作", description = "资金操作")
public class UserOperateIntegralMoneyRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "uid")
    @NotNull
    @Min(value = 1, message = "请输入正确的uid")
    private Integer uid;

    @Schema(description = "积分类型， 1 = 增加， 2 = 减少")
    @NotNull
    @Range(min = 1, max = 2, message = "请选择正确的类型， 【1 = 增加， 2 = 减少】")
    private Integer integralType;

    @Schema(description = "积分")
    @Min(value = 0)
    @Max(value = 999999)
    private Integer integralValue;

    @Schema(description = "余额类型， 1 = 增加， 2 = 减少")
    @NotNull
    @Range(min = 1, max = 2, message = "请选择正确的类型， 【1 = 增加， 2 = 减少】")
    private Integer moneyType;

    @Schema(description = "余额")
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "999999.99")
    private BigDecimal moneyValue;

}
