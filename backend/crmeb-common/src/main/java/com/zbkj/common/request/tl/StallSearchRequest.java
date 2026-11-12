package com.zbkj.common.request.tl;

import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/** Public stall discovery filters. */
@Data
public class StallSearchRequest {
    private String cityCode;
    private String districtCode;
    private Integer categoryId;
    @Size(max = 120)
    private String keyword;
    private Boolean todayOpen;
    private Boolean openNow;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private BigDecimal lat;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private BigDecimal lng;

    @Min(1)
    @Max(50000)
    private Integer radiusMeters;

    /** Supported values: recent, distance. */
    private String sort;

    @Min(1)
    private Integer page = 1;

    @Min(1)
    @Max(100)
    private Integer limit = 20;
}
