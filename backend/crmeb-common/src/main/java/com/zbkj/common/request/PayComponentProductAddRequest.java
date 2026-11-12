package com.zbkj.common.request;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 组件商品添加Request对象
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
@Schema(name = "PayComponentProductAddRequest对象", description = "组件商品添加Request对象")
public class PayComponentProductAddRequest implements Serializable {

    private static final long serialVersionUID = -2196197495866986580L;

    @Schema(description = "商品ID(更新时必填)")
    private Integer id;

    @Schema(description = "主商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请先选择主商品")
    private Integer primaryProductId;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "标题不能为空")
    private String title;

    @Schema(description = "轮播图,多张", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "轮播图不能为空")
    private String headImg;

    @Schema(description = "商品资质图片,多张")
    private String qualificationPics;

    @Schema(description = "第三级类目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "第三级类目不能为空")
    private Integer thirdCatId;

    @Schema(description = "品牌id")
    private Integer brandId;

    @Schema(description = "商品详情")
    private String descInfo;

    @Schema(description = "商品详情图片,多图")
    private String descImgs;

    @Schema(description = "运费模板ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "运费模板不能为空")
    private Integer tempId;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "单位名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String unitName;

    @Schema(description = "获得积分", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer giveIntegral;

    @Schema(description = "虚拟销量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer ficti;

    @Schema(description = "规格 0单 1多", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean specType;

    @Schema(description = "商品属性")
    private List<StoreProductAttrAddRequest> attr;

    @Schema(description = "商品属性详情")
    private List<StoreProductAttrValueAddRequest> attrValue;
}
