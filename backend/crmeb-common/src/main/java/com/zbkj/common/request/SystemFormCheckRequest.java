package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 整体保存表单数据
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
@Schema(name = "SystemFormCheckRequest对象", description = "整体保存表单数据")
public class SystemFormCheckRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "表单名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "请选择表单")
    private Integer id;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(description = "状态（1：开启；0：关闭；）")
    private Boolean status;

    @Schema(description = "字段值列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "fields 至少要有一组数据")
    @Size(min = 1, message = "fields 至少要有一组数据")
    private List<SystemFormItemCheckRequest> fields;

}
