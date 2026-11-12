package com.zbkj.common.request.tl;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/** User supplied data only; owner, source and moderation fields come from the server. */
@Data
public class StallSubmissionRequest {
    @NotBlank
    @Size(max = 120)
    private String name;
    @NotNull
    private Integer categoryId;
    @Size(max = 2000)
    private String description;
    @Size(max = 500)
    private String address;
    @Size(max = 32)
    private String cityCode;
    @Size(max = 32)
    private String districtCode;
    private BigDecimal longitude;
    private BigDecimal latitude;
    @Size(max = 500)
    private String coverImage;
    @Size(max = 4500)
    private String imageUrls;
    @Size(max = 500)
    private String businessHours;
    @Size(max = 64)
    private String contactPhone;
}
