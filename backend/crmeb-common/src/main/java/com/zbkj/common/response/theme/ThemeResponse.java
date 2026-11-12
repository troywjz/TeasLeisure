package com.zbkj.common.response.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 主题列表响应对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ThemeResponse对象", description = "主题列表响应对象")
public class ThemeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "主题名称")
    private String title;

    @Schema(description = "主题简介")
    private String info;

    @Schema(description = "主题预览地址")
    private String showUrl;

    @Schema(description = "主题类型，自建主题/广场主题")
    private String type;

    @Schema(description = "首页图片")
    private String homeImage;

    @Schema(description = "首页数据来源id")
    private Integer homeDataId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "首页数据更新时间")
    private Date homeDataUpdateTime;

    @Schema(description = "分类页图片")
    private String categoryImage;

    @Schema(description = "分类页数据来源id")
    private Integer categoryDataId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "分类页数据更新时间")
    private Date categoryDataUpdateTime;

    @Schema(description = "商城详情页图片")
    private String detailImage;

    @Schema(description = "商品详情页数据来源id")
    private Integer detailDataId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "商品详情页数据更新时间")
    private Date detailDataUpdateTime;

    @Schema(description = "个人中心图片")
    private String userImage;

    @Schema(description = "个人中心数据来源id")
    private Integer userDataId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "个人中心数据更新时间")
    private Date userDataUpdateTime;

    @Schema(description = "主题风格数据来源id")
    private Integer themeDataId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "主题风格数据更新时间")
    private Date themeDataUpdateTime;

    @Schema(description = "首页默认图片")
    private String homeDefaultImage;

    @Schema(description = "分类页默认图片")
    private String categoryDefaultImage;

    @Schema(description = "商城详情页默认图片")
    private String detailDefaultImage;

    @Schema(description = "个人中心默认图片")
    private String userDefaultImage;

    @Schema(description = "页面类型：theme主题，micro微页面")
    private String pageType;

    @Schema(description = "是否使用")
    private Integer isUse;

    @Schema(description = "是否删除")
    private Integer isDel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "添加时间")
    private Date addTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private Date upTime;
}
