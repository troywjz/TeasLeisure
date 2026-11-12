package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * ios登录请求体
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
@Schema(name = "IosLoginRequest对象", description = "ios登录请求体")
public class IosLoginRequest {

    @Schema(description = "iosToken", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotBlank(message = "identityToken不能为空")
    private String identityToken;

    @Schema(description = "App服务商唯一用户标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "openId不能为空")
    private String openId;

    @Schema(description = "Ios用户电子邮箱")
//    @NotBlank(message = "email不能为空")
    private String email;
}
