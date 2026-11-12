package com.zbkj.common.request;

import com.zbkj.common.annotation.StringContains;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.*;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 *  购物车数量请求对象
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
@Schema(name = "CartRequest对象", description = "购物车数量请求对象")
public class CartNumRequest implements Serializable {

    private static final long serialVersionUID = -1186533756329913311L;

    @Schema(description = "数量类型：total-商品数量，sum-购物数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "数量类型不能为空")
    @StringContains(limitValues = {"total", "sum"}, message = "无效数量类型")
    private String type;

    @Schema(description = "商品类型：true-有效商品，false-无效商品", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品类型不能为空")
    private Boolean numType;

}
