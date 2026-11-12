package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 登录用户修改信息请求对象
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
@Schema(name = "LoginAdminUpdateRequest", description = "登录用户修改信息请求对象")
public class LoginAdminUpdateRequest {

    @Schema(description = "后台管理员昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 16, message = "后台管理员昵称长度不能超过16个字符")
    private String realName;

}
