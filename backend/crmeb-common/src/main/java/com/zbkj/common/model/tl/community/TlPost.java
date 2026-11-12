package com.zbkj.common.model.tl.community;
import com.baomidou.mybatisplus.annotation.IdType; import com.baomidou.mybatisplus.annotation.TableId; import com.baomidou.mybatisplus.annotation.TableName; import lombok.Data; import lombok.experimental.Accessors; import java.util.Date;
@Data @Accessors(chain=true) @TableName("eb_tl_post")
public class TlPost {
 @TableId(type=IdType.AUTO) private Long id;
 private Long stallId; private Integer authorId; private String content; private String imageUrls;
 private Integer auditStatus; private Date createTime; private Date updateTime; private Boolean isDeleted;
}
