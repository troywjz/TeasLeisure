package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;

/**
 * 一号通登录对象
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
@Schema(name = "OnePassLoginVo对象", description = "一号通登录对象")
public class OnePassLoginVo {

    private static final long serialVersionUID=1L;

    @Schema(description = "access_key一号通后台AccessKey管理获得", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accessKey;

    @Schema(description = "secret_key一号通后台AccessKey管理获得", requiredMode = Schema.RequiredMode.REQUIRED)
    private String secretKey;
}
