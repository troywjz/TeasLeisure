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
 * 定时任务
 * </p>
 *
 * @author HZW
 * @since 2021-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_schedule_job")
@Schema(name = "ScheduleJob对象", description = "定时任务")
public class ScheduleJob implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "任务id")
    @TableId(value = "job_id", type = IdType.AUTO)
    private Integer jobId;

    @Schema(description = "spring bean名称")
    private String beanName;

    @Schema(description = "方法名")
    private String methodName;

    @Schema(description = "参数")
    private String params;

    @Schema(description = "cron表达式")
    private String cronExpression;

    @Schema(description = "任务状态  0：正常  1：暂停")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "是否删除")
    private Boolean isDelte;

    @Schema(description = "创建时间")
    private Date createTime;


}
