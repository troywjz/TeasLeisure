package com.zbkj.common.request.onepass;

import com.zbkj.common.constants.RegularConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 一号通账号修改
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
@Schema(name = "OnePassUpdateRequest对象", description = "一号通账号修改")
public class OnePassUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "账号不能为空")
    private String account;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = RegularConstants.PHONE_TWO, message = "请填写正确的手机号")
    private String phone;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "验证码不能为空")
    private String code;
}
