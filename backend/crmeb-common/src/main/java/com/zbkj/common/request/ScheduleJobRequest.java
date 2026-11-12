package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 定时任务请求对象
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
@Schema(name = "ScheduleJobRequest对象", description = "定时任务请求对象")
public class ScheduleJobRequest implements Serializable {

    private static final long serialVersionUID = -452373239606480650L;

    @Schema(description = "任务id,编辑时必传")
    private Integer jobId;

    @Schema(description = "spring bean名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "spring bean名称不能为空")
    private String beanName;

    @Schema(description = "方法名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "方法名不能为空")
    private String methodName;

    @Schema(description = "参数")
    private String params;

    @Schema(description = "cron表达式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    @Schema(description = "备注")
    private String remark;
}
