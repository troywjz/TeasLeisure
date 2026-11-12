package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 商品库存
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
@Schema(name = "StoreProductStockRequest对象", description = "库存修改")
public class StoreProductStockRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择商品")
    private Integer productId;

    private Integer seckillId;

    private Integer bargainId;

    private Integer combinationId;

    @Schema(description = "商品属性ID集合", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择商品属性id集合")
    private Integer attrId;

    @Schema(description = "类型， 增加 add | 减少 diff", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请选择类型")
    private String operationType;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "请填写数量")
    private Integer num;

    @Schema(description = "商品类型 0=普通 1=秒杀", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer type;

    @Schema(description = "商品SKU信息")
    private String suk;
}
