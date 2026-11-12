package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 文章管理 Request
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
@Schema(name = "ArticleRequest对象", description = "文章管理表")
public class ArticleRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请选择分类")
    private String cid;

    @Schema(description = "文章标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请填写文章标题")
    @Length(max = 200, message = "文章标题最多200个字符")
    private String title;

    @Schema(description = "文章作者", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请填写文章作者")
    @Length(max = 50, message = "文章作者最多50个字符")
    private String author;

    @Schema(description = "文章图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请上传文章图片")
    @Length(max = 255, message = "文章图片名称最多255个字符")
    private String imageInput;

    @Schema(description = "文章简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(max = 200, message = "文章简介最多200个字符")
    @NotBlank(message = "请填写文章简介")
    private String synopsis;

    @Schema(description = "文章分享标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请填写文章分享标题")
    @Length(max = 200, message = "文章分享标题最多200个字符")
    private String shareTitle;

    @Schema(description = "文章分享简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请填写文章分享简介")
    @Length(max = 200, message = "文章分享简介最多200个字符")
    private String shareSynopsis;

    @Schema(description = "是否热门(小程序)", example = "false")
    @NotNull(message = "是否热门(小程序)不能为空")
    private Boolean isHot;

    @Schema(description = "是否轮播图(小程序)", example = "true")
    @NotNull(message = "是否轮播图(小程序)不能为空")
    private Boolean isBanner;

    @Schema(description = "文章内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请填写文章内容")
    private String content;
}
