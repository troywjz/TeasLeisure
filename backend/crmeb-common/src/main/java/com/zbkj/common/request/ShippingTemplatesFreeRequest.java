package com.zbkj.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 免费模版 Request
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
@TableName("eb_shipping_templates_free")
@Schema(name = "ShippingTemplatesFreeRequest对象", description = "免费")
public class ShippingTemplatesFreeRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "城市ID, 多个逗号分割。 全国 all", requiredMode = Schema.RequiredMode.REQUIRED, example = "1,2,3,4")
    @NotNull(message = "请选择城市")
    private String cityId;

    @Schema(description = "城市名称描述")
    private String title;

    @Schema(description = "包邮件数", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
//    @Min(value = 1, message = "请填写包邮件数")
    @DecimalMin(value = "0.1", message = "包邮不能低于0.1")
    private BigDecimal number;

    @Schema(description = "包邮金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "0.1")
    @NotNull(message = "请填写包邮金额")
    @DecimalMin(value = "0", message = "包邮金额不能低于0")
    private BigDecimal price;
}
