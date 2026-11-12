package com.zbkj.common.model.record;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商城商品日记录表
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
@TableName("eb_shopping_product_day_record")
@Schema(name = "ShoppingProductDayRecord对象", description = "商城商品日记录表")
public class ShoppingProductDayRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "日期")
    private String date;

    @Schema(description = "新增商品数量")
    private Integer addProductNum;

    @Schema(description = "浏览量")
    private Integer pageView;

    @Schema(description = "收藏量")
    private Integer collectNum;

    @Schema(description = "加购件数")
    private Integer addCartNum;

    @Schema(description = "下单商品数")
    private Integer orderProductNum;

    @Schema(description = "交易成功商品数")
    private Integer orderSuccessProductNum;


}
