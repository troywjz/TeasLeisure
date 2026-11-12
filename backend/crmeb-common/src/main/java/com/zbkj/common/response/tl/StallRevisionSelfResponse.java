package com.zbkj.common.response.tl;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/** Allowlisted revision history for its authenticated submitter. */
@Data
@Accessors(chain = true)
public class StallRevisionSelfResponse {
    private Long id;
    private Long stallId;
    private String cityCode;
    private String districtCode;
    private String cityName;
    private String districtName;
    private String stallName;
    private String address;
    private String coverImage;
    private String imageUrls;
    private Integer status;
    private String auditNote;
    private Date createTime;
    private Date auditTime;
}
