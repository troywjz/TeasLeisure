package com.zbkj.common.model.tl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 茶余地摊主档。摊位长期资料与动态内容分开维护。
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_tl_stall")
public class Stall implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer categoryId;
    private String description;
    private String address;
    private String cityCode;
    private String districtCode;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String coverImage;
    private String imageUrls;
    private String businessHours;
    private String contactPhone;
    /** 0 pending, 1 published, 2 rejected, 3 hidden */
    private Integer auditStatus;
    /** user submitted, merchant submitted, or operator created */
    private String sourceType;
    private Long createdBy;
    private Integer ownerUserId;
    private Integer auditedBy;
    private String auditNote;
    private Date auditTime;
    private Date createTime;
    private Date updateTime;
    private Boolean isDeleted;
}
