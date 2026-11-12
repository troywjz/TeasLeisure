package com.zbkj.common.response.tl.conversation;

import lombok.Data;
import java.util.Date;

@Data
public class TlConversationResponse {
    private Long id;
    private Long stallId;
    private String stallName;
    private String peerName;
    private String lastMessageContent;
    private Date lastMessageTime;
    private Integer unreadCount;
    private Boolean canSend;
}
