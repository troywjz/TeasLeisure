package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Login Response
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
@Schema(name = "LoginResponse", description = "用户登录返回数据")
public class LoginResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户登录密钥")
    private String token;

    @Schema(description = "状态:login-登录，register-注册,start-注册起始页")
    private String type;

    @Schema(description = "注册key")
    private String key;

    @Schema(description = "登录用户Uid")
    private Integer uid;

    @Schema(description = "登录用户昵称")
    private String nikeName;

    @Schema(description = "登录用户手机号")
    private String phone;
}
