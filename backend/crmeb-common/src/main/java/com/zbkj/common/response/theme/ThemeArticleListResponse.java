package com.zbkj.common.response.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 主题文章列表响应对象
 */
@Data
@Schema(name = "ThemeArticleListResponse对象", description = "主题文章列表响应对象")
public class ThemeArticleListResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文章列表")
    private List<ThemeArticleResponse> list = new ArrayList<>();

    @Schema(description = "总数")
    private Long count = 0L;
}
