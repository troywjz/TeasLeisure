package com.zbkj.common.response.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 主题文章分类响应对象
 */
@Data
@Schema(name = "ThemeArticleCategoryResponse对象", description = "主题文章分类响应对象")
public class ThemeArticleCategoryResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分类ID")
    private Integer id;

    @Schema(description = "父级ID")
    private Integer pid;

    @Schema(description = "分类名称")
    private String title;
}
