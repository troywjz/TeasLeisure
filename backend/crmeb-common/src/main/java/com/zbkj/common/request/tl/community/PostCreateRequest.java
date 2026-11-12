package com.zbkj.common.request.tl.community;
import lombok.Data; import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.NotNull; import jakarta.validation.constraints.Size;
@Data public class PostCreateRequest { @NotNull private Long stallId; @NotBlank @Size(max=2000) private String content; @Size(max=2000) private String imageUrls; }
