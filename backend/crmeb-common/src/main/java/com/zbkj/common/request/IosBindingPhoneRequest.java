package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 微信绑定手机号请求对象
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
@Schema(name = "IosBindingPhoneRequest", description = "Ios绑定手机号请求对象")
public class IosBindingPhoneRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String captcha;
}
