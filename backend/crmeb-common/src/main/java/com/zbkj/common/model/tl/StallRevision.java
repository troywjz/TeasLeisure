package com.zbkj.common.model.tl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("eb_tl_stall_revision")
public class StallRevision implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO) private Long id;
    private Long stallId;
    private Long pendingKey;
    private Integer editorUserId;
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
    private Integer status;
    private Integer auditedBy;
    private String auditNote;
    private Date auditTime;
    private Date createTime;
}
