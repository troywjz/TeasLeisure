package com.zbkj.common.response.tl;

import lombok.Data;
import java.util.Date;

/** Authenticated submitter/owner view; moderation operator identity stays private. */
@Data
public class StallSelfResponse {
    private Long id;
    private String name;
    private Integer categoryId;
    private String description;
    private String address;
    private String cityCode;
    private String districtCode;
    private String cityName;
    private String districtName;
    private String coverImage;
    private String imageUrls;
    private String businessHours;
    /** Only returned to the authenticated submitter or claimed owner. */
    private String contactPhone;
    private Integer auditStatus;
    /** Whether the authenticated user is the current claimed owner; never exposes ownerUserId. */
    private Boolean claimedByMe;
    /** True only for a published, undeleted stall that has no owner yet. */
    private Boolean claimable;
    private String auditNote;
    private Date auditTime;
    private Date createTime;
    private Date updateTime;
    private Boolean todayStatusKnown;
    private Boolean openToday;
    private String todayHours;
}
