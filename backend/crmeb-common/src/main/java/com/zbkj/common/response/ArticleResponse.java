package com.zbkj.common.response;

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
 * 文章响应对象
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
@Schema(name = "ArticleResponse对象", description = "文章响应对象")
public class ArticleResponse implements Serializable {

    private static final long serialVersionUID = -4585094537501770138L;

    @Schema(description = "文章管理ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "分类id")
    private String cid;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章作者")
    private String author;

    @Schema(description = "文章图片 前端用")
    private String imageInput;

    @Schema(description = "文章简介")
    private String synopsis;

    @Schema(description = "浏览次数")
    private String visit;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
}
