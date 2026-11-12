package com.zbkj.common.response.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 主题文本字段项响应对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ThemeTextFieldItemResponse对象", description = "主题文本字段项响应对象")
public class ThemeTextFieldItemResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "字段展示名称")
    private String label;

    @Schema(description = "字段标识")
    private String value;
}
