package com.zbkj.common.model.tl;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
@Data @Accessors(chain=true) @TableName("eb_tl_content_report")
public class TlContentReport {
 @TableId(type=IdType.AUTO) private Long id;
 private String targetType;
 private Long targetId;
 private Integer reporterUserId;
 private String reason;
 private Integer status;
 private Boolean hideTarget;
 private Integer handledBy;
 private String handleNote;
 private Date handleTime;
 private Date createTime;
 @TableField(exist = false) private String targetSummary;
 @TableField(exist = false) private String targetAddress;
 @TableField(exist = false) private String relatedContent;
 @TableField(exist = false) private String relatedStallName;
 @TableField(exist = false) private String relatedStallAddress;
}
