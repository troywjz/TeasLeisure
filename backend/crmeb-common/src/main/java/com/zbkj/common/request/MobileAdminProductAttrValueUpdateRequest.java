package com.zbkj.common.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 移动端商家管理商品规格价格库存更新请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "MobileAdminProductAttrValueUpdateRequest", description = "移动端商家管理商品规格价格库存更新请求")
public class MobileAdminProductAttrValueUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("attr_value")
    @JsonAlias("attrValue")
    @Schema(description = "规格价格库存列表")
    private List<AttrValueItem> attrValue = new ArrayList<>();

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Schema(name = "MobileAdminProductAttrValueUpdateRequest.AttrValueItem", description = "规格价格库存项")
    public static class AttrValueItem implements Serializable {

        private static final long serialVersionUID = 1L;

        @Schema(description = "规格ID")
        private Integer id;

        @Schema(description = "规格唯一值")
        private String unique;

        @Schema(description = "成本价")
        private BigDecimal cost;

        @Schema(description = "销售价")
        private BigDecimal price;

        @JsonProperty("ot_price")
        @JsonAlias("otPrice")
        @Schema(description = "原价")
        private BigDecimal otPrice;

        @Schema(description = "库存")
        private Integer stock;
    }
}
