package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.zbkj.common.model.category.Category;
import com.zbkj.common.request.StoreCouponRequest;
import com.zbkj.common.model.product.StoreProduct;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 优惠券记录表
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
@Schema(name = "StoreCouponInfoResponse对象", description = "优惠券详情")
public class StoreCouponInfoResponse implements Serializable {

    private static final long serialVersionUID=1L;

    public StoreCouponInfoResponse(StoreCouponRequest coupon, List<StoreProduct> product, List<Category> category) {
        this.coupon = coupon;
        this.product = product;
        this.category = category;
    }

    @Schema(description = "优惠券发布id")
    private StoreCouponRequest coupon;

    @Schema(description = "商品信息")
    private List<StoreProduct> product;

    @Schema(description = "分类信息")
    private List<Category> category;

}
