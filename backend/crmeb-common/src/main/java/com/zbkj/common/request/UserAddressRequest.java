package com.zbkj.common.request;

import com.zbkj.common.constants.RegularConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 新增用户地址对象
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
@Schema(name = "UserAddressRequest对象", description = "新增用户地址对象")
public class UserAddressRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户地址id")
    private Integer id;

    @Schema(description = "收货人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "收货人姓名不能为空")
    @Length(max = 32, message = "收货人姓名不能超过32个字符")
    private String realName;

    @Schema(description = "收货人电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "收货人电话不能为空")
    @Pattern(regexp = RegularConstants.PHONE_TWO, message = "请填写正确的收货人电话")
    private String phone;

    @Schema(description = "收货人详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "收货人详细地址不能为空")
    @Length(max = 256, message = "收货人详细地址不能超过32个字符")
    private String detail;

    @Schema(description = "是否默认", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isDefault;

    @Valid
    @Schema(description = "城市信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private UserAddressCityRequest address;
}
