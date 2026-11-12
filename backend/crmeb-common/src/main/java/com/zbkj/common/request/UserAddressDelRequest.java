package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.Min;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 用户地址表
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
@Schema(name = "UserAddressDelRequest对象", description = "用户地址")
public class UserAddressDelRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户地址id")
    @Min(value = 1, message = "请选择用户地址")
    private Integer id;
}
