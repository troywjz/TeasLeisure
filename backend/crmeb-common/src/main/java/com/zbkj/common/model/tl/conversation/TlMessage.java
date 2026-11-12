package com.zbkj.common.model.tl.conversation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("eb_tl_message")
public class TlMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO) private Long id;
    private Long conversationId;
    private Integer senderUserId;
    private String content;
    private Date createTime;
    private Boolean isDeleted;
}
