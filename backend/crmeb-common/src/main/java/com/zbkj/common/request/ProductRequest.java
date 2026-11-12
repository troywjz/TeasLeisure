package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 商品搜索
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
@Schema(name = "ProductRequest对象", description = "商品搜索")
public class ProductRequest implements Serializable {

    private static final long serialVersionUID = 3481659942630712958L;

    @Schema(description = "搜索关键字")
    private String keyword;

    @Schema(description = "商品名称")
    private String storeName;

    @Schema(description = "分类id")
    private String cid;

    @Schema(description = "价格排序", allowableValues = "range[asc,desc]")
    private String priceOrder;

    @Schema(description = "销量排序", allowableValues = "range[asc,desc]")
    private String salesOrder;

    @Schema(description = "是否新品")
    private Boolean news;
}
