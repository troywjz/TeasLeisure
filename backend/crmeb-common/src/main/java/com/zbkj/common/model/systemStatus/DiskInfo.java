package com.zbkj.common.model.systemStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// 磁盘信息
@Data
public class DiskInfo {

    @Schema(description = "磁盘路径")
    private String path;

    @Schema(description = "文件系统")
    private String fileSystem;

    @Schema(description = "盘符类型")
    private String type;

    @Schema(description = "总大小")
    private String totalSize;

    @Schema(description = "可用大小")
    private String freeSize;

    @Schema(description = "已用大小")
    private String usedSize;

    @Schema(description = "已用百分比")
    private double usedPercentage;

}
