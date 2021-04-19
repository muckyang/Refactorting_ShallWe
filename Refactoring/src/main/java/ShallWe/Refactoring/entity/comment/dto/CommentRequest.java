package ShallWe.Refactoring.entity.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentRequest {
    @NotBlank
    private Long orderId;
    @NotBlank
    private Long userId;
    @NotBlank
    private String content;
}
