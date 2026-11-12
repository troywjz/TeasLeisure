package com.zbkj.common.model.tl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("eb_tl_stall_daily_status")
public class StallDailyStatus {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long stallId;
    private java.sql.Date statusDate;
    private Boolean isOpen;
    private String hours;
    private Integer updatedBy;
    private Date createTime;
    private Date updateTime;
}
