package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

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
public class SystemConfigFormItemConfigVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "")
    private String label;

    @Schema(description = "")
    private String showLabel;

    @Schema(description = "")
    private String changeTag;

    @Schema(description = "")
    private String labelWidth;

    @Schema(description = "")
    private String tag;

    @Schema(description = "")
    private String tagIcon;

    @Schema(description = "")
    private String span;

    @Schema(description = "")
    private String layout;

    @Schema(description = "")
    private Boolean required;

    @Schema(description = "验证规则")
    private List<SystemConfigFormItemConfigRegListVo> regList;

    @Schema(description = "")
    private String document;

    @Schema(description = "")
    private String formId;

    @Schema(description = "")
    private String renderKey;

    @Schema(description = "")
    private String defaultValue;


}
