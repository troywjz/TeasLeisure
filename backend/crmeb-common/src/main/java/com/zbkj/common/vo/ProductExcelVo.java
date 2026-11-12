package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品ExcelVo对象类
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
@Schema(name = "ProductExcelVo对象", description = "产品导出")
public class ProductExcelVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "商品名称")
    private String storeName;

//    @Schema(description = "商品简介")
//    private String storeInfo;

    @Schema(description = "商品分类")
    private String cateName;

    @Schema(description = "价格")
    private String price;

    @Schema(description = "库存")
    private String stock;

    @Schema(description = "销量")
    private String sales;

    @Schema(description = "浏览量")
    private String browse;
}
