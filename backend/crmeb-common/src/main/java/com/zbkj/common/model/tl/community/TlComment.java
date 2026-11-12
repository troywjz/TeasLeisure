package com.zbkj.common.model.tl.community;
import com.baomidou.mybatisplus.annotation.IdType; import com.baomidou.mybatisplus.annotation.TableId; import com.baomidou.mybatisplus.annotation.TableName; import lombok.Data; import lombok.experimental.Accessors; import java.util.Date;
@Data @Accessors(chain=true) @TableName("eb_tl_comment")
public class TlComment {
 @TableId(type=IdType.AUTO) private Long id;
 private Long postId; private Integer authorId; private Long parentId; private String content;
 private Integer auditStatus; private Date createTime; private Boolean isDeleted;
}
