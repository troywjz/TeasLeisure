package com.zbkj.common.model.product;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品属性详情表
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
@TableName("eb_store_product_attr_result")
@Schema(name = "StoreProductAttrResult对象", description = "商品属性详情表")
public class StoreProductAttrResult implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "商品ID")
    private Integer productId;

    @Schema(description = "商品属性参数")
    private String result;

    @Schema(description = "上次修改时间")
    private Integer changeTime;

    @Schema(description = "活动类型 0=商品，1=秒杀，2=砍价，3=拼团")
    private Integer type;


}
