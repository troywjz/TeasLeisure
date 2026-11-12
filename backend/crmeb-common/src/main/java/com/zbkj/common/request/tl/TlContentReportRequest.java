package com.zbkj.common.request.tl;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Data
public class TlContentReportRequest {
 @NotBlank private String targetType;
 @NotNull private Long targetId;
 @NotBlank @Size(max=500) private String reason;
}
