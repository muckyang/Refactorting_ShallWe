package ShallWe.Refactoring.entity.order.dto;

import ShallWe.Refactoring.entity.comment.Comment;
import ShallWe.Refactoring.entity.order.Category;
import ShallWe.Refactoring.entity.order.OrderStatus;
import ShallWe.Refactoring.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
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
}
