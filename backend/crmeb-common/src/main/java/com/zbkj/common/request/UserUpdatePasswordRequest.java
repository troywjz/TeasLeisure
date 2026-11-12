package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 后台修改用户密码请求对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserUpdatePasswordRequest", description = "后台修改用户密码请求对象")
public class UserUpdatePasswordRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "用户ID不能为空")
    private Integer id;

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 18, message = "密码长度必须为6~18位")
    private String password;
}
