package com.zbkj.common.response.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 主题文本字段响应对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ThemeTextFieldResponse对象", description = "主题文本字段响应对象")
public class ThemeTextFieldResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户数据可绑定字段")
    private List<ThemeTextFieldItemResponse> user;

    @Schema(description = "文章数据可绑定字段")
    private List<ThemeTextFieldItemResponse> article;

    @Schema(description = "优惠券数据可绑定字段")
    private List<ThemeTextFieldItemResponse> coupon;

    @Schema(description = "商品数据可绑定字段")
    private List<ThemeTextFieldItemResponse> product;
}
