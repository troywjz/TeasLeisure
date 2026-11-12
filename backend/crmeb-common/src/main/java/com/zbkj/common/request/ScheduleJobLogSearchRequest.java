package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 定时任务日志搜索请求参数
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
@Schema(name = "ScheduleJobLogSearchRequest对象", description = "定时任务日志搜索请求参数")
public class ScheduleJobLogSearchRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "任务id")
    private Integer jobId;

    @Schema(description = "spring bean名称")
    private String beanName;

    @Schema(description = "方法名")
    private String methodName;

    @Schema(description = "任务状态    1：成功    0：失败")
    private Integer status;

}
