package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 登录配置响应对象
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
@Schema(name = "LoginConfigResponse对象", description = "登录配置响应对象")
public class LoginConfigResponse implements Serializable {

    private static final long serialVersionUID = -4585094537501770138L;

    @Schema(description = "小程序手机号校验类型（多选）")
    private String routinePhoneVerification;

    @Schema(description = "公众号登录方式(单选)")
    private String publicLoginType;

    @Schema(description = "移动端登录Logo")
    private String mobileLoginLogo;

    @Schema(description = "公司名称")
    private String siteName;

    @Schema(description = "公司图片")
    private String companyImage;

}
