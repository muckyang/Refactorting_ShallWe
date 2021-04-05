package ShallWe.Refactoring.entity.order.dto;

import ShallWe.Refactoring.entity.comment.Comment;
import ShallWe.Refactoring.entity.order.Category;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.OrderStatus;
import ShallWe.Refactoring.entity.tag.Tag;
import ShallWe.Refactoring.entity.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(of={"id","title","tags"})
public class OrderResponse {
    private Long id;
    private User user;
    private String title;
    private String description;
    private int goalPrice;
    private int sumPrice;
    private int likeCount;
    private int commentCount;
    private OrderStatus status;
    private Category category;
    private LocalDateTime createTime;
    private List<Tag> tags = new ArrayList<>();

    public OrderResponse(Order order){
        this.id  = order.getId();
        this.user  = order.getUser();
        this.title  = order.getTitle();
        this.description  = order.getDescription();
        this.goalPrice  = order.getGoalPrice();
        this.sumPrice  = order.getSumPrice();
        this.likeCount  = order.getLikeCount();
        this.commentCount  = order.getCommentCount();
        this.status  = order.getStatus();
        this.category  = order.getCategory();
        this.createTime = order.getCreateTime();
        this.tags=order.getTags();
    }

}
