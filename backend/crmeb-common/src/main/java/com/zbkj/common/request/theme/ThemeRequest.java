package com.zbkj.common.request.theme;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 主题新增/修改请求
 */
@Data
@Schema(name = "ThemeRequest对象", description = "主题新增/修改请求")
public class ThemeRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请填写版本号")
    @Length(max = 255, message = "版本号长度不能超过255个字符")
    private String version;

    @Schema(description = "主题名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请填写主题名称")
    @Length(max = 255, message = "主题名称长度不能超过255个字符")
    private String title;

    @Schema(description = "主题简介")
    @Length(max = 255, message = "主题简介长度不能超过255个字符")
    private String info;

    @Schema(description = "主题类型，0:自建主题，1:广场主题")
    private Integer type;

    @Schema(description = "首页数据")
    private String homeData;

    @Schema(description = "首页图片")
    @Length(max = 255, message = "首页图片长度不能超过255个字符")
    private String homeImage;

    @Schema(description = "首页数据来源id")
    private Integer homeDataId;

    @Schema(description = "首页数据更新时间")
    private Integer homeDataUpdateTime;

    @Schema(description = "分类页数据")
    private String categoryData;

    @Schema(description = "分类页图片")
    @Length(max = 255, message = "分类页图片长度不能超过255个字符")
    private String categoryImage;

    @Schema(description = "分类页数据来源id")
    private Integer categoryDataId;

    @Schema(description = "分类页数据更新时间")
    private Integer categoryDataUpdateTime;

    @Schema(description = "商品详情页数据")
    private String detailData;

    @Schema(description = "商城详情页图片")
    @Length(max = 255, message = "商城详情页图片长度不能超过255个字符")
    private String detailImage;

    @Schema(description = "商品详情页数据来源id")
    private Integer detailDataId;

    @Schema(description = "商品详情页数据更新时间")
    private Integer detailDataUpdateTime;

    @Schema(description = "个人中心数据")
    private String userData;

    @Schema(description = "个人中心图片")
    @Length(max = 255, message = "个人中心图片长度不能超过255个字符")
    private String userImage;

    @Schema(description = "个人中心数据来源id")
    private Integer userDataId;

    @Schema(description = "个人中心数据更新时间")
    private Integer userDataUpdateTime;

    @Schema(description = "主题风格数据")
    private String themeData;

    @Schema(description = "主题风格数据来源id")
    private Integer themeDataId;

    @Schema(description = "主题风格数据更新时间")
    private Integer themeDataUpdateTime;

    @Schema(description = "首页默认数据")
    private String homeDefaultData;

    @Schema(description = "首页默认图片")
    @Length(max = 255, message = "首页默认图片长度不能超过255个字符")
    private String homeDefaultImage;

    @Schema(description = "分类页默认数据")
    private String categoryDefaultData;

    @Schema(description = "分类页默认图片")
    @Length(max = 255, message = "分类页默认图片长度不能超过255个字符")
    private String categoryDefaultImage;

    @Schema(description = "商品详情页默认数据")
    private String detailDefaultData;

    @Schema(description = "商城详情页默认图片")
    @Length(max = 255, message = "商城详情页默认图片长度不能超过255个字符")
    private String detailDefaultImage;

    @Schema(description = "个人中心默认数据")
    private String userDefaultData;

    @Schema(description = "个人中心默认图片")
    @Length(max = 255, message = "个人中心默认图片长度不能超过255个字符")
    private String userDefaultImage;

    @Schema(description = "主题风格默认数据")
    private String themeDefaultData;

    @Schema(description = "页面类型：theme主题，micro微页面")
    @Length(max = 255, message = "页面类型长度不能超过255个字符")
    private String pageType;

    @Schema(description = "是否使用")
    private Integer isUse;
}
