package com.zbkj.common.request;

import com.zbkj.common.constants.RegularConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 手机号注册
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
@Schema(name = "LoginMobileRequest对象", description = "手机号注册")
public class LoginMobileRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegularConstants.PHONE_TWO, message = "手机号码格式错误")
    private String phone;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = RegularConstants.VALIDATE_CODE_NUM_SIX, message = "验证码格式错误，验证码必须为6位数字")
    private String captcha;

    @Schema(description = "推广人id")
    @JsonProperty(value = "spread_spid")
    private Integer spreadPid;



}
