package com.zbkj.common.model.tl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/** Append-only history for TeasLeisure moderation state transitions. */
@Data
@Accessors(chain = true)
@TableName("eb_tl_moderation_event")
public class TlModerationEvent {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String targetType;
    private Long targetId;
    private Long relatedId;
    private Integer fromStatus;
    private Integer toStatus;
    private String action;
    private String actorType;
    private Integer actorId;
    private String actorAccount;
    private String reason;
    private Date createTime;
}
