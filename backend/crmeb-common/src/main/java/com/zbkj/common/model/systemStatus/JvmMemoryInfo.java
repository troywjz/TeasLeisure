package com.zbkj.common.model.systemStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
// JVM内存
@Data
public class JvmMemoryInfo {

    @Schema(description = "JVM总内存")
    private String jvmTotal;

    @Schema(description = "JVM已用内存")
    private String jvmUsed;

    @Schema(description = "JVM剩余内存")
    private String jvmFree;

    @Schema(description = "JVM使用率")
    private double jvmUsageRate;
}
