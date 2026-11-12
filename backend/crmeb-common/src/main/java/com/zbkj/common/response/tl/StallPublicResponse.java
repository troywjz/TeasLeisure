package com.zbkj.common.response.tl;

import lombok.Data;

import java.math.BigDecimal;

/** Public allowlist; moderation, ownership and private contact fields stay server-side. */
@Data
public class StallPublicResponse {
    private Long id;
    private String name;
    private Integer categoryId;
    private String description;
    private String address;
    private String cityCode;
    private String districtCode;
    private String cityName;
    private String districtName;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String coverImage;
    private String imageUrls;
    private String businessHours;
    private Boolean todayStatusKnown;
    private Boolean openToday;
    private String todayHours;
    private Boolean currentOpen;
}
