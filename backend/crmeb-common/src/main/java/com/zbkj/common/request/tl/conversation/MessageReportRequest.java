package com.zbkj.common.request.tl.conversation;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class MessageReportRequest {
    @NotNull private Long conversationId;
    private Long messageId;
    @NotBlank @Size(max = 500) private String reason;
}
