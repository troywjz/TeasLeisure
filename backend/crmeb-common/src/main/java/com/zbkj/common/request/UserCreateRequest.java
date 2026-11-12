package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 后台管理员新增H5用户请求对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserCreateRequest", description = "后台新增H5用户请求对象")
public class UserCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确")
    private String phone;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    private String pwd;

    @Schema(description = "是否为推广员", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否为推广员不能为空")
    private Boolean isPromoter;

    @Schema(description = "状态：true=正常，false=禁止", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态不能为空")
    private Boolean status;
}
