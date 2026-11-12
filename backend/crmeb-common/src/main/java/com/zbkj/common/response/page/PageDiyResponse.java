package com.zbkj.common.response.page;

import io.swagger.v3.oas.annotations.media.Schema;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * DIY数据表
 * </p>
 *
 * @author dazongzi
 * @since 2023-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "PageDiy Response 对象", description = "DIY数据表")
public class PageDiyResponse implements Serializable {

    private static final long serialVersionUID=1L;

//    @TableId(value = "id", type = IdType.AUTO)
//    private Integer id;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "页面名称")
    private String name;

    @Schema(description = "网站标题")
    private String title;

    @Schema(description = "封面图")
    private String coverImage;

    @Schema(description = "模板名称")
    private String templateName;

    @Schema(description = "页面数据")
    private JSONObject value;

    @Schema(description = "默认数据")
    private String defaultValue;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "是否使用")
    private Integer status;

    @Schema(description = "页面类型")
    private Integer type;

    @Schema(description = "显示首页")
    private Integer isShow;

    @Schema(description = "颜色是否选中")
    private Integer isBgColor;

    @Schema(description = "背景图是否选中")
    private Integer isBgPic;

    @Schema(description = "是否是diy数据")
    private Integer isDiy;

    @Schema(description = "背景颜色")
    private String colorPicker;

    @Schema(description = "背景图")
    private String bgPic;

    @Schema(description = "背景图图片样式")
    private Integer bgTabVal;

    @Schema(description = "是否删除")
    private Integer isDel;

    @Schema(description = "返回地址")
    private String returnAddress;

    @Schema(description = "标题背景色")
    private String titleBgColor;

    @Schema(description = "标题颜色")
    private String titleColor;

    @Schema(description = "商家样式")
    private Integer serviceStatus;

    @Schema(description = "商户ID")
    private Integer merId;

    @Schema(description = "默认模板(1.平台默认 2.商户默认）")
    private Integer isDefault;


}
