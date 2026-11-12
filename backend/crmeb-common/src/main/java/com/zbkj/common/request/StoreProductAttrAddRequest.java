package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 商品属性添加对象
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
@Schema(name = "StoreProductAttrAddRequest对象", description = "商品属性添加对象")
public class StoreProductAttrAddRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "attrID|新增时不填，修改时必填")
    private Integer id;

    @Schema(description = "属性名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "规格名不能为空")
    private String attrName;

    @Schema(description = "属性值|逗号分隔", requiredMode = Schema.RequiredMode.REQUIRED)
    private String attrValues;

    @Schema(description = "是否展示规格图片")
    @NotNull(message = "请选择是否展示规格图片")
    private Boolean isShowImage;

    @Schema(description = "规格属性数组")
    //@NotEmpty(message = "规格属性数组不能为空")
    @Valid
    private List<ProductAttrOptionAddRequest> optionList;
}
