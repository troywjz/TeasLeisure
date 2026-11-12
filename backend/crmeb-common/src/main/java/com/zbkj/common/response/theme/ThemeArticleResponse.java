package com.zbkj.common.response.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 主题文章响应对象
 */
@Data
@Schema(name = "ThemeArticleResponse对象", description = "主题文章响应对象")
public class ThemeArticleResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文章ID")
    private Integer id;

    @Schema(description = "分类ID")
    private String cid;

    @Schema(description = "文章标题")
    private String title;

    @JsonProperty(value = "image_input")
    @Schema(description = "文章图片")
    private List<String> imageInput;

    @Schema(description = "浏览次数")
    private String visit;

    @JsonProperty(value = "add_time")
    @Schema(description = "添加时间")
    private String addTime;
}
