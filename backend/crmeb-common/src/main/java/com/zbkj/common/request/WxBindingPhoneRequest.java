package com.zbkj.common.request;

import com.zbkj.common.constants.RegularConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@Schema(name = "WxBindingPhoneRequest", description = "微信绑定手机号请求对象")
public class WxBindingPhoneRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = RegularConstants.PHONE_TWO, message = "请输入正确的手机号")
    private String phone;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = RegularConstants.VALIDATE_CODE_NUM_SIX, message = "请输入6位验证码")
    private String captcha;

    @Schema(description = "类型:public-公众号，routine-小程序,iosWx-苹果微信，androidWx-安卓微信, ios-ios登录")
    @NotBlank(message = "类型不能为空")
    private String type;

    @Schema(description = "新用户登录时返回的key")
    @NotBlank(message = "key不能为空")
    private String key;

    @Schema(description = "小程序获取手机号加密数据")
    private String encryptedData;

    @Schema(description = "加密算法的初始向量")
    private String iv;

    @Schema(description = "小程序登录code，来自wx.login()，用于获取session_key")
    private String code;

    @Schema(description = "兼容前端字段：小程序登录code，优先于code使用")
    private String wxCode;

    @Schema(description = "微信手机号授权code，新版手机号授权接口返回")
    private String phoneCode;
}
