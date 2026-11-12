package com.zbkj.common.request.theme;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 主题图片保存请求
 */
@Data
@Schema(name = "ThemeImageRequest对象", description = "主题图片保存请求")
public class ThemeImageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "图片所属模块类型：home/detail/user", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请选择图片所属模块类型")
    private String type;

    @Schema(description = "图片地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请上传图片")
    @Length(max = 255, message = "图片地址长度不能超过255个字符")
    private String image;
}
