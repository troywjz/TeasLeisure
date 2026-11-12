package com.zbkj.common.model.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 主题表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_theme")
@Schema(name = "Theme对象", description = "主题表")
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "自增ID")
    private Integer id;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "主题名称")
    private String title;

    @Schema(description = "主题简介")
    private String info;

    @Schema(description = "主题类型，0:自建主题，1:广场主题")
    private Integer type;

    @Schema(description = "首页数据")
    private String homeData;

    @Schema(description = "首页图片")
    private String homeImage;

    @Schema(description = "首页数据来源id")
    private Integer homeDataId;

    @Schema(description = "首页数据更新时间")
    private Integer homeDataUpdateTime;

    @Schema(description = "分类页数据")
    private String categoryData;

    @Schema(description = "分类页图片")
    private String categoryImage;

    @Schema(description = "分类页数据来源id")
    private Integer categoryDataId;

    @Schema(description = "分类页数据更新时间")
    private Integer categoryDataUpdateTime;

    @Schema(description = "商品详情页数据")
    private String detailData;

    @Schema(description = "商城详情页图片")
    private String detailImage;

    @Schema(description = "商品详情页数据来源id")
    private Integer detailDataId;

    @Schema(description = "商品详情页数据更新时间")
    private Integer detailDataUpdateTime;

    @Schema(description = "个人中心数据")
    private String userData;

    @Schema(description = "个人中心图片")
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
    private String homeDefaultImage;

    @Schema(description = "分类页默认数据")
    private String categoryDefaultData;

    @Schema(description = "分类页默认图片")
    private String categoryDefaultImage;

    @Schema(description = "商品详情页默认数据")
    private String detailDefaultData;

    @Schema(description = "商城详情页默认图片")
    private String detailDefaultImage;

    @Schema(description = "个人中心默认数据")
    private String userDefaultData;

    @Schema(description = "个人中心默认图片")
    private String userDefaultImage;

    @Schema(description = "主题风格默认数据")
    private String themeDefaultData;

    @Schema(description = "页面类型：theme主题，micro微页面")
    private String pageType;

    @Schema(description = "是否使用")
    private Integer isUse;

    @Schema(description = "是否删除")
    private Integer isDel;

    @Schema(description = "添加时间")
    private Integer addTime;

    @Schema(description = "更新时间")
    private Integer upTime;
}
