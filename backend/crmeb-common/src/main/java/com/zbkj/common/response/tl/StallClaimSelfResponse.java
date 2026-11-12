package com.zbkj.common.response.tl;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/** Safe stall summary and review state for the authenticated applicant. */
@Data
@Accessors(chain = true)
public class StallClaimSelfResponse {
    private Long id;
    private Long stallId;
    private String cityCode;
    private String districtCode;
    private String cityName;
    private String districtName;
    private String stallName;
    private String address;
    private String coverImage;
    private Integer status;
    private String auditNote;
    private Date auditTime;
    private Date createTime;
}
