package com.zbkj.common.model.systemStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// 服务器信息
@Data
public class ServerInfo {

    @Schema(description = "服务器名称")
    private String serverName;

    @Schema(description = "服务器IP")
    private String serverIP;

    @Schema(description = "操作系统")
    private String os;

    @Schema(description = "系统架构")
    private String systemArch;
}
