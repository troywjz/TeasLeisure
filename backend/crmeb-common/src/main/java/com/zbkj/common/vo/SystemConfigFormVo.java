package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * SystemConfigFormVo对象
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
@Schema(name = "SystemConfigFormVo对象", description = "form对象")
public class SystemConfigFormVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "表单名称")
    private String formRef;

    @Schema(description = "form对象")
    private String formModel;

    @Schema(description = "大小")
    private String size;

    @Schema(description = "label位置")
    private String labelPosition;

    @Schema(description = "label宽度")
    private String labelWidth;

    @Schema(description = "form规则")
    private String formRules;

    @Schema(description = "")
    private String gutter;

    @Schema(description = "是否禁用")
    private String disabled;

    @Schema(description = "span")
    private String span;

    @Schema(description = "button")
    private String formBtns;

    @Schema(description = "字段值列表")
    private List<String> fields;



}
