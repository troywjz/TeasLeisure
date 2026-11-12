package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 商品添加库存对象
 * +----------------------------------------------------------------------
 * | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 * +----------------------------------------------------------------------
 * | Copyright (c) 2016~2025 https://www.crmeb.com All rights reserved.
 * +----------------------------------------------------------------------
 * | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 * +----------------------------------------------------------------------
 * | Author: CRMEB Team <admin@crmeb.com>
 * +----------------------------------------------------------------------
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ProductAddStockRequest对象", description = "商品添加库存对象")
public class ProductAddStockRequest implements Serializable {

    private static final long serialVersionUID = -259657957101524526L;

    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品ID不能为空")
    private Integer id;

    @Valid
    @Schema(description = "商品规格属性列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "商品规格属性列表不能为空")
    private List<ProductAttrValueAddStockRequest> attrValueList;

}
