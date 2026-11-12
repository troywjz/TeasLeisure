package com.zbkj.common.request.tl.community;
import lombok.Data; import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.Size;
@Data public class CommentCreateRequest { private Long parentId; @NotBlank @Size(max=1000) private String content; }
