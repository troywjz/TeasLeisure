package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 用户地址详细对象
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
@Schema(name = "UserAddressCityRequest对象", description = "用户地址详细对象")
public class UserAddressCityRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "收货人所在省", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "收货人所在省不能为空")
    private String province;

    @Schema(description = "收货人所在市", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "收货人所在市不能为空")
    private String city;

    @Schema(description = "城市id")
    private Integer cityId = 0;

    @Schema(description = "收货人所在区", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "收货人所在区不能为空")
    private String district;

}
