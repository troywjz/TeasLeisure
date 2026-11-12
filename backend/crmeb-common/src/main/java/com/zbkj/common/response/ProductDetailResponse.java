package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.zbkj.common.model.product.StoreProduct;
import com.zbkj.common.model.product.StoreProductAttr;
import com.zbkj.common.model.product.StoreProductGuarantee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 商品详情
 * +----------------------------------------------------------------------
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
@Schema(name = "ProductDetailResponse对象", description = "商品详情H5")
public class ProductDetailResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "产品属性")
    private List<StoreProductAttr> productAttr;

    @Schema(description = "商品属性详情")
    private HashMap<String, StoreProductAttrValueResponse> productValue;

    @Schema(description = "返佣金额区间")
    private String priceName;

    @Schema(description = "为移动端特定参数 所有参与的活动")
    private List<ProductActivityItemResponse> activityAllH5;

    @Schema(description = "商品信息")
    private StoreProduct productInfo;

    @Schema(description = "收藏标识")
    private Boolean userCollect;

    @Schema(description = "保障服务")
    private List<StoreProductGuarantee> guaranteeList;
}
