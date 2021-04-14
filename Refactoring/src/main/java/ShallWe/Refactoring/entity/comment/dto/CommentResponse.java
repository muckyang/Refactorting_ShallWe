package ShallWe.Refactoring.entity.comment.dto;

import ShallWe.Refactoring.entity.comment.Comment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CommentResponse {
    private Long orderId;
    private Long userId;
    private String content;
    private String status;

    @QueryProjection
    public CommentResponse(Comment comment){
        orderId = comment.getOrder().getId();
        userId = comment.getUser().getId();
        content = comment.getContent();
        status = comment.getStatus().name();

    }
}
