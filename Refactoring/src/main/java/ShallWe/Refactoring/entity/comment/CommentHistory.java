package ShallWe.Refactoring.entity.comment;


import ShallWe.Refactoring.entity.order.Order;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//TODO 댓글 수정시 수정 전 내용 저장소
@Entity
@Getter
@NoArgsConstructor
@Table(name = "Comment_history")
public class CommentHistory {
    @Id
    @GeneratedValue
    @Column(name = "comment_history_id")
    private Long id;
    private Long orderId;
    private Long commentId;
    private Long userId;
    private String content;
    private String status;

    @QueryProjection
    public CommentHistory(Comment comment) {
        commentId = comment.getId();
        orderId = comment.getOrder().getId();
        userId = comment.getUser().getId();
        content = comment.getContent();
        status = comment.getStatus().name();
    }
}
