package com.zbkj.common.response.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 主题产品分类响应对象
 */
@Data
@Schema(name = "ThemeCategoryTreeResponse对象", description = "主题产品分类响应对象")
public class ThemeCategoryTreeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分类ID")
    private Integer id;

    @Schema(description = "分类值")
    private Integer value;

    @Schema(description = "分类名称")
    private String label;

    @Schema(description = "分类标题")
    private String title;

    @Schema(description = "父级ID")
    private Integer pid;

    @Schema(description = "子级分类")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ThemeCategoryTreeResponse> children;
}
