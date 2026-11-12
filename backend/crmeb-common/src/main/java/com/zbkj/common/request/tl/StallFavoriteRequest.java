package com.zbkj.common.request.tl;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class StallFavoriteRequest {
    @NotNull private Long stallId;
}
