package com.zbkj.common.model.systemStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// 系统内存
@Data
public class systemMemoryInfo {


    @Schema(description = "总内存")
    private String systemTotal;

    @Schema(description = "已用内存")
    private String systemUsed;

    @Schema(description = "剩余内存")
    private String systemFree;

    @Schema(description = "使用率")
    private double systemUsageRate;
}
