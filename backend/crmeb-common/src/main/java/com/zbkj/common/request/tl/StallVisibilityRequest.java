package com.zbkj.common.request.tl;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class StallVisibilityRequest {
    @NotBlank
    @Size(max = 500)
    private String reason;
}
