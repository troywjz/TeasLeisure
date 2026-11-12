package com.zbkj.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * <p>
 * 商品规格属性添加对象
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ProductAttrOptionAddRequest", description = "商品规格属性添加对象")
public class ProductAttrOptionAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID，新增时不填，修改时必填")
    private Integer id;

    @Schema(description = "属性名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "属性名不能为空")
    @Size(max = 30, message = "属性名长度不能超过30个字符")
    @JsonProperty("value")
    private String optionName;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "排序")
    private Integer sort;
}
