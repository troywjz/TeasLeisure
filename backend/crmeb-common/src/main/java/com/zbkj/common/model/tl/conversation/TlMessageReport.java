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
@TableName("eb_tl_message_report")
public class TlMessageReport implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO) private Long id;
    private Long conversationId;
    private Long messageId;
    private Integer reporterUserId;
    private String reason;
    private Integer status;
    private Integer handledBy;
    private String handleNote;
    private Date handleTime;
    private Date createTime;
}
