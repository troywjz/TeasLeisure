package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 短信修改签名请求对象
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
@Schema(name = "SmsModifySignRequest对象", description = "短信修改签名请求对象")
public class SmsModifySignRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "新的短信签名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "短信签名不能为空")
    private String sign;

    @Schema(description = "账号绑定的手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @Schema(description = "短信验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "短信验证码不能为空")
    private String code;

}
