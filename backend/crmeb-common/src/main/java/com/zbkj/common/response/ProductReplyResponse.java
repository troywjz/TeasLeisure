package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 商品评论H5详情响应对象
 * +----------------------------------------------------------------------
 * | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 * +----------------------------------------------------------------------
 * | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 * +----------------------------------------------------------------------
 * | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 * +----------------------------------------------------------------------
 * | Author: CRMEB Team <admin@crmeb.com>
 * +----------------------------------------------------------------------
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ProductReplyResponse对象", description = "商品评论H5详情响应对象")
public class ProductReplyResponse {
    private static final long serialVersionUID=1L;

    @Schema(description = "评论ID")
    private Integer id;

    @Schema(description = "用户ID")
    private Integer uid;

    @Schema(description = "商品分数")
    private Integer score;

    @Schema(description = "评论内容")
    private String comment;

    @Schema(description = "评论图片")
    private List<String> pics;

    @Schema(description = "管理员回复内容")
    private String merchantReplyContent;

    @Schema(description = "用户名称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "评论时间")
    private Date createTime;

    @Schema(description = "商品规格属性值")
    private String sku;
}
