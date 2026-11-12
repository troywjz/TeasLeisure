package com.zbkj.common.request.tl;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class StallTodayStatusRequest {
    @NotNull
    private Boolean isOpen;

    @Size(max = 80)
    private String hours;
}
