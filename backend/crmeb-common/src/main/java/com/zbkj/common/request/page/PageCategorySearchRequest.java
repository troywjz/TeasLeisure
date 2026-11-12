package com.zbkj.common.request.page;

import lombok.Data;


import io.swagger.v3.oas.annotations.media.Schema;
/**
 * @Auther: 大粽子
 * @Date: 2023/5/16 11:23
 * @Description: 页面DIY和设计用到的分类
 */
@Data
public class PageCategorySearchRequest {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "父类id")
    private Integer pid;

    @Schema(description = "类型:link、special、product、product_category、custom")
    private String type;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "层级 个位数")
    private Boolean level;

    @Schema(description = "是否商户 0平台，商户id")
    private Integer isMer;
}
