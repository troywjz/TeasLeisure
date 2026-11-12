package com.zbkj.common.request.tl;

import lombok.Data;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class StallDuplicateSearchRequest {
    private Long excludeId;
    @Size(max = 120) private String name;
    @Size(max = 500) private String address;
    @Size(max = 32) private String cityCode;
    @Size(max = 32) private String districtCode;
    @DecimalMin("-90.0") @DecimalMax("90.0") private BigDecimal lat;
    @DecimalMin("-180.0") @DecimalMax("180.0") private BigDecimal lng;
}
