package ShallWe.Refactoring.entity.comment.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private Long orderId;
    private Long userId;
    private String content;
}
