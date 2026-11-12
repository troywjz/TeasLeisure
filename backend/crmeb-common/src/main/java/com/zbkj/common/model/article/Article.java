package com.zbkj.common.model.article;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章管理表
 *  +----------------------------------------------------------------------
 *  | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 *  +----------------------------------------------------------------------
 *  | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 *  +----------------------------------------------------------------------
 *  | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 *  +----------------------------------------------------------------------
 *  | Author: CRMEB Team <admin@crmeb.com>
 *  +----------------------------------------------------------------------
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_article")
@Schema(name = "Article对象", description = "文章管理表")
public class Article implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "文章管理ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "分类id")
    private String cid;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章作者")
    private String author;

    @Schema(description = "文章图片")
    private String imageInput;

    @Schema(description = "文章简介")
    private String synopsis;

    @Schema(description = "文章分享标题")
    private String shareTitle;

    @Schema(description = "文章分享简介")
    private String shareSynopsis;

    @Schema(description = "浏览次数")
    private Integer visit;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "原文链接")
    private String url;

    @Schema(description = "微信素材id")
    private String mediaId;

    @Schema(description = "状态")
    private Boolean status;

    @Schema(description = "是否隐藏")
    private Boolean hide;

    @Schema(description = "管理员id")
    private Integer adminId;

    @Schema(description = "商户id")
    private Integer merId;

    @Schema(description = "商品关联id")
    private Integer productId;

    @Schema(description = "是否热门(小程序)")
    private Boolean isHot;

    @Schema(description = "是否轮播图(小程序)")
    private Boolean isBanner;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "创建时间")
    private Date updateTime;


}
