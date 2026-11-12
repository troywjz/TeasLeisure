package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * item对象
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
@Schema(name = "SystemConfigFormItemVo对象", description = "item对象")
public class SystemConfigFormItemVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "配置")
    private SystemConfigFormItemConfigVo __config__;

    @Schema(description = "")
    private String placeholder;

    @Schema(description = "")
    private String step;

    @Schema(description = "")
    private String stepStrictly;

    @Schema(description = "")
    private String controlsPosition;

    @Schema(description = "")
    private String disabled;

    @Schema(description = "")
    private String __vModel__;



}
