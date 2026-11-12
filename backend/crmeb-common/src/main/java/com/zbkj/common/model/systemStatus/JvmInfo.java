package com.zbkj.common.model.systemStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;
// JVM信息
@Data
public class JvmInfo {

    @Schema(description = "Java名称")
    private String javaName;

    @Schema(description = "Java版本")
    private String javaVersion;

    @Schema(description = "启动时间")
    private Date startTime;

    @Schema(description = "运行时长")
    private String uptime;

    @Schema(description = "安装路径")
    private String installPath;

    @Schema(description = "项目路径")
    private String projectPath;

    @Schema(description = "运行参数")
    private List<String> jvmOptions;
}
