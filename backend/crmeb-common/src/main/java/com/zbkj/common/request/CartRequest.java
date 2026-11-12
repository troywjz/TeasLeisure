package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 *  添加购物车参数
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
@Schema(name = "CartRequest对象", description = "购物车")
public class CartRequest {
    private static final long serialVersionUID=1L;

    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品id不能为空")
    private Integer productId;

    @Schema(description = "商品属性 -- attr 对象的id")
    @NotBlank(message = "商品属性id不能为空")
    private String productAttrUnique;

    @Schema(description = "商品数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量不能小于1")
    @Max(value = 99, message = "单个商品数量不能大于99")
    private Integer cartNum;
}
