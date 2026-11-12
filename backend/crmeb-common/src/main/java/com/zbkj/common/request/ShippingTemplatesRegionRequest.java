package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 运费模板区域请求对象
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
@Schema(name = "ShippingTemplatesRegionRequest对象", description = "运费模板区域请求对象")
public class ShippingTemplatesRegionRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "城市ID（只要最后一级城市ID）, 多个逗号分割。全国 0", requiredMode = Schema.RequiredMode.REQUIRED, example = "1,2,3,4")
    @NotNull(message = "请选择城市")
    private String cityId;

    @Schema(description = "城市名称描述")
    private String title;

    @Schema(description = "首件", requiredMode = Schema.RequiredMode.REQUIRED, example = "0.1")
    @DecimalMin(value = "0.1", message = "首件金额不能低于0.1")
    private BigDecimal first;

    @Schema(description = "首件运费", requiredMode = Schema.RequiredMode.REQUIRED, example = "0.1")
    @DecimalMin(value = "0.1", message = "首件运费金额不能低于0.1")
    private BigDecimal firstPrice;

    @Schema(description = "续件", requiredMode = Schema.RequiredMode.REQUIRED, example = "0.1")
    @DecimalMin(value = "0.1", message = "续件不能低于0.1")
    private BigDecimal renewal;

    @Schema(description = "续件运费", requiredMode = Schema.RequiredMode.REQUIRED, example = "0.1")
    @DecimalMin(value = "0.1", message = "续件运费金额不能低于0.1")
    private BigDecimal renewalPrice;

    @Schema(description = "分组唯一值")
    private String uniqid;
}
