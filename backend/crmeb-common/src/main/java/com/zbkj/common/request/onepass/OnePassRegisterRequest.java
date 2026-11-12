package com.zbkj.common.request.onepass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 一号通账号注册
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
@Schema(name = "OnePassRegisterRequest对象", description = "一号通账号注册")
public class OnePassRegisterRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "账号不能为空")
    private String account;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "手机号不能为空")
    private String phone;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "密码不能为空")
    private String password;

    @Schema(description = "域名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "域名不能为空")
    private String domain;

    @Schema(description = "验证码")
    @NotNull(message = "验证码不能为空")
    private String code;
}
