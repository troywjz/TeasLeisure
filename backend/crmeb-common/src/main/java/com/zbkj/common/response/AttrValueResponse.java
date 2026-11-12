package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品属性值响应对象
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
@Schema(name = "AttrValueResponse对象", description = "商品属性值响应对象")
public class AttrValueResponse implements Serializable {

    private static final long serialVersionUID=1L;
    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "商品ID")
    private Integer productId;

    @Schema(description = "sku")
    private String suk;

    @Schema(description = "属性对应的库存")
    private Integer stock;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "属性金额")
    private BigDecimal price;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "成本价")
    private BigDecimal cost;

    @Schema(description = "原价")
    private BigDecimal otPrice;

    @Schema(description = "重量")
    private BigDecimal weight;

    @Schema(description = "体积")
    private BigDecimal volume;

    @Schema(description = "一级返佣")
    private BigDecimal brokerage;

    @Schema(description = "二级返佣")
    private BigDecimal brokerageTwo;

    @Schema(description = "活动类型 0=商品，1=秒杀，2=砍价，3=拼团")
    private Integer type;

    @Schema(description = "活动限购数量")
    private Integer quota;

    @Schema(description = "活动限购数量显示")
    private Integer quotaShow;

    @Schema(description = "attrValue字段")
    private String attrValue;

    @Schema(description = "砍价商品最低价|砍价专用字段")
    private BigDecimal minPrice;

    @Schema(description = "商品条码")
    private String barCode;

    @Schema(description = "是否默认")
    private Boolean isDefault;

    @Schema(description = "是否显示")
    private Boolean isShow;

    @Schema(description = "属性数组(前端使用)")
    private String[] attrArr;
}
