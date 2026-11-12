package com.zbkj.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 定时任务日志
 * </p>
 *
 * @author HZW
 * @since 2021-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_schedule_job_log")
@Schema(name = "ScheduleJobLog对象", description = "定时任务日志")
public class ScheduleJobLog implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "任务日志id")
    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;

    @Schema(description = "任务id")
    private Integer jobId;

    @Schema(description = "spring bean名称")
    private String beanName;

    @Schema(description = "方法名")
    private String methodName;

    @Schema(description = "参数")
    private String params;

    @Schema(description = "任务状态    0：成功    1：失败")
    private Integer status;

    @Schema(description = "失败信息")
    private String error;

    @Schema(description = "耗时(单位：毫秒)")
    private Integer times;

    @Schema(description = "创建时间")
    private Date createTime;


}
