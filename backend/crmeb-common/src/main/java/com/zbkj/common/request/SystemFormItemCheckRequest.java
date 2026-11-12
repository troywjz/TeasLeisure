package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 配置表表单字段明细
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
@Schema(name = "SystemFormItemCheckRequest对象", description = "表单字段明细")
public class SystemFormItemCheckRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请设置 SystemFormItemCheckRequest 对象的 name 属性")
    private String name;

    @Schema(description = "字段值", requiredMode = Schema.RequiredMode.REQUIRED)
    private String value;

    @Schema(description = "字段显示文字", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

}
