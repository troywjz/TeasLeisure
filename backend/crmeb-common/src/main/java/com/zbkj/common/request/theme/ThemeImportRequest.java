package com.zbkj.common.request.theme;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 主题导入请求
 */
@Data
@Schema(name = "ThemeImportRequest对象", description = "主题导入请求")
public class ThemeImportRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "上传后的ZIP文件路径", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请上传主题ZIP文件")
    private String url;
}
