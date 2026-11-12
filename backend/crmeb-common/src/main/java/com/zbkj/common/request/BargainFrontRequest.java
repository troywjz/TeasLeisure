package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * H5 砍价公共请求对象
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
@Schema(name = "BargainFrontRequest对象", description = "砍价公共请求对象")
public class BargainFrontRequest {

    @Schema(description = "砍价商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "砍价商品编号不能为空")
    private Integer bargainId;

    @Schema(description = "用户砍价活动ID")
    private Integer bargainUserId;

    @Schema(description = "用户砍价活动Uid")
    private Integer bargainUserUid;
}
