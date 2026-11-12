package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 上报支付的场景信息
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
@Schema(name = "CreateOrderH5SceneInfoVo对象", description = "上报支付的场景信息")
public class CreateOrderH5SceneInfoVo {
    public CreateOrderH5SceneInfoVo() {
    }

    public CreateOrderH5SceneInfoVo(CreateOrderH5SceneInfoDetailVo h5Info) {
        this.h5_info = h5Info;
    }

    @Schema(description = "h5_info", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "h5_info")
    private CreateOrderH5SceneInfoDetailVo h5_info;
}
