package com.zbkj.common.model.tl.community;
import com.baomidou.mybatisplus.annotation.IdType; import com.baomidou.mybatisplus.annotation.TableId; import com.baomidou.mybatisplus.annotation.TableName; import lombok.Data; import lombok.experimental.Accessors; import java.util.Date;
@Data @Accessors(chain=true) @TableName("eb_tl_reaction")
public class TlReaction { @TableId(type=IdType.AUTO) private Long id; private Long postId; private Integer userId; private String reactionType; private Date createTime; }
