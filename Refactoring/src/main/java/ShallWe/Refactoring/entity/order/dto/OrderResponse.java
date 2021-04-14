package ShallWe.Refactoring.entity.order.dto;

import ShallWe.Refactoring.entity.comment.Comment;
import ShallWe.Refactoring.entity.order.Category;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString(of={"id","userId","title","description"})
public class OrderResponse {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private int goalPrice;
    private int sumPrice;
    private int likeCount;
    private int commentCount;
    private OrderStatus status;
    private Category category;
    private LocalDateTime createTime;

    @QueryProjection
    public OrderResponse(Order order){
        this.id  = order.getId();
        this.userId  = order.getUser().getId();
        this.title  = order.getTitle();
        this.description  = order.getDescription();
        this.goalPrice  = order.getGoalPrice();
        this.sumPrice  = order.getSumPrice();
        this.likeCount  = order.getLikeCount();
        this.commentCount  = order.getCommentCount();
        this.status  = order.getStatus();
        this.category  = order.getCategory();
        this.createTime = order.getCreateTime();
    }

}
