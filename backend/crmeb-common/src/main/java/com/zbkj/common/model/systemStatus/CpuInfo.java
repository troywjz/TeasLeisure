package com.zbkj.common.model.systemStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
// CPU信息
@Data
public class CpuInfo {

    @Schema(description = "核心数")
    private int coreCount;

    @Schema(description = "用户使用率")
    private double userUsageRate;

    @Schema(description = "系统使用率")
    private double systemUsageRate;

    @Schema(description = "当前空闲率")
    private double freeUsageRate;
}
