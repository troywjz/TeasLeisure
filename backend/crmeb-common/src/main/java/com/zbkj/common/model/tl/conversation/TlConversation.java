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
@TableName("eb_tl_conversation")
public class TlConversation implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO) private Long id;
    private Long stallId;
    private Integer visitorUserId;
    private Integer ownerUserId;
    private Long lastMessageId;
    private Date lastMessageTime;
    private Long visitorReadMessageId;
    private Long ownerReadMessageId;
    private Date createTime;
    private Date updateTime;
    private Boolean isClosed;
}
