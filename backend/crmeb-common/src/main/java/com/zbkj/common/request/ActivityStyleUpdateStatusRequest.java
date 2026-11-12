package com.zbkj.common.request;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 活动边框 更新状态
 * @Auther: 大粽子
 * @Date: 2023/1/5 15:38
 * @Description: 描述对应的业务场景
 */
@Data
public class ActivityStyleUpdateStatusRequest {
    @Schema(description = "主键")
    @NotNull(message = "id 不能为空")
    private Integer id;

    @Schema(description = "状态 0=关闭1=开启")
    private Boolean status;
}
