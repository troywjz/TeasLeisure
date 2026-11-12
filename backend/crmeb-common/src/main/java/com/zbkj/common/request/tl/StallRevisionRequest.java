package com.zbkj.common.request.tl;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class StallRevisionRequest {
    @NotBlank @Size(max = 120) private String name;
    private Integer categoryId;
    @Size(max = 2000) private String description;
    @Size(max = 500) private String address;
    @Size(max = 32) private String cityCode;
    @Size(max = 32) private String districtCode;
    private BigDecimal longitude;
    private BigDecimal latitude;
    @Size(max = 500) private String coverImage;
    @Size(max = 4500) private String imageUrls;
    @Size(max = 500) private String businessHours;
    @Size(max = 64) private String contactPhone;
}
