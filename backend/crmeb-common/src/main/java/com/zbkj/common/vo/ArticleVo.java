package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章管理 Vo
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
@Schema(name = "ArticleVo对象", description = "文章管理表")
public class ArticleVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "文章管理ID")
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

    @Schema(description = "浏览次数")
    private String visit;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date updateTime;
}
