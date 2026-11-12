package com.zbkj.common.response.tl;

import lombok.Data;

@Data
public class StallDuplicateCandidateResponse {
    private Long id;
    private String name;
    private String address;
    private String cityCode;
    private String districtCode;
    private String coverImage;
    private Integer auditStatus;
    private String sourceType;
}
