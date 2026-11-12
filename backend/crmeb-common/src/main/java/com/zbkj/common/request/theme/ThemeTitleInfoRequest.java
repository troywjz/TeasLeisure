package com.zbkj.common.request.theme;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 主题标题信息保存请求
 */
@Data
@Schema(name = "ThemeTitleInfoRequest对象", description = "主题标题信息保存请求")
public class ThemeTitleInfoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主题名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请填写主题名称")
    @Length(max = 255, message = "主题名称长度不能超过255个字符")
    private String title;

    @Schema(description = "主题简介")
    @Length(max = 255, message = "主题简介长度不能超过255个字符")
    private String info;

    @Schema(description = "页面类型：theme主题，micro微页面")
    @JsonProperty("page_type")
    private String pageType;
}
