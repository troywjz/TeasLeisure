package com.zbkj.common.request.tl;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class StallClaimRequest {
    @NotNull
    private Long stallId;
    @NotBlank
    @Size(max = 1000)
    private String proof;
    @Size(max = 2504)
    private String proofImageUrls;
}
