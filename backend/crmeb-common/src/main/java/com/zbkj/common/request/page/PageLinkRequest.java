package com.zbkj.common.request.page;

import lombok.Data;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * @Auther: 大粽子
 * @Date: 2023/5/16 11:28
 * @Description: 页面设计和DIY用到的链接查询对象
 */
@Data
public class PageLinkRequest {
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "分类id")
    private Integer cateId;

    @Schema(description = "分组1:基础2:分销3:个人中心")
    private Boolean type;

    @Schema(description = "页面名称")
    private String name;

    @Schema(description = "页面链接")
    private String url;

    @Schema(description = "参数")
    private String param;

    @Schema(description = "事例")
    private String example;

    @Schema(description = "状态")
    private Boolean status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "1是商户的链接")
    private Boolean isMer;
}
