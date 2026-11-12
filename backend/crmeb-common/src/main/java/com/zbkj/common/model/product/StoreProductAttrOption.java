package com.zbkj.common.model.product;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品规格属性表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product_attr_option")
@Schema(name = "StoreProductAttrOption对象", description = "商品规格属性表")
public class StoreProductAttrOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "商品ID")
    private Integer productId;

    @Schema(description = "商品规格ID")
    private Integer attrId;

    @Schema(description = "属性名")
    @JsonProperty("value")
    private String optionName;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否删除,0-否，1-是")
    private Boolean isDel;
}
