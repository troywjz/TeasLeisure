package com.zbkj.common.request.tl.conversation;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class MessageSendRequest {
    @NotBlank @Size(max = 2000) private String content;
}
