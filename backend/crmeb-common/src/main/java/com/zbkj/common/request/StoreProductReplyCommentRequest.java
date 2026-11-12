package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 回复商品评论对象
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
@Schema(name = "StoreProductReplyCommentRequest对象", description = "回复商品评论对象")
public class StoreProductReplyCommentRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "评论id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "评论id不能为空")
    private Integer ids;

    @Schema(description = "管理员回复内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请填写评论内容")
    private String merchantReplyContent;
}
