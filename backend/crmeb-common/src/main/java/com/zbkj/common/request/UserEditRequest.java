package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 用户编辑Request
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
@Schema(name = "UserEditRequest对象", description = "修改个人资料")
public class UserEditRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户昵称")
    @NotBlank(message = "请填写用户昵称")
    @Length(max = 255, message = "用户昵称不能超过255个字符")
    private String nickname;

    @Schema(description = "用户头像")
    @NotBlank(message = "请上传用户头像")
    @Length(max = 255, message = "用户头像不能超过255个字符")
    private String avatar;
}
