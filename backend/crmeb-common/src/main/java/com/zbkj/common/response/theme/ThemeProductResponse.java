package com.zbkj.common.response.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 主题商品列表响应对象
 */
@Data
@Schema(name = "ThemeProductResponse对象", description = "主题商品列表响应对象")
public class ThemeProductResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品ID")
    private Integer id;

    @JsonProperty(value = "store_name")
    @Schema(description = "商品名称")
    private String storeName;

    @JsonProperty(value = "cate_id")
    @Schema(description = "分类ID")
    private String cateId;

    @Schema(description = "商品图片")
    private String image;

    @Schema(description = "销量")
    private String sales;

    @Schema(description = "商品价格")
    private BigDecimal price;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "活动显示排序 0=默认，1=秒杀，2=砍价，3=拼团")
    private String activity;

    @JsonProperty(value = "ot_price")
    @Schema(description = "市场价")
    private BigDecimal otPrice;

    @JsonProperty(value = "spec_type")
    @Schema(description = "规格 0单 1多")
    private Integer specType;

    @JsonProperty(value = "unit_name")
    @Schema(description = "单位名")
    private String unitName;
}
