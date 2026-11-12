package com.zbkj.common.request.page;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * @Auther: 大粽子
 * @Date: 2023/5/16 11:44
 * @Description: 页面分类和DIY用到的实体参数
 */
@Data
public class PageDiyEditNameRequest {

    @Schema(description = "id")
    @NotNull(message = "模版id 不能为空")
    private Integer id;

    @Schema(description = "页面名称")
    @NotEmpty(message = "模版名称 不能为空")
    private String name;
}
