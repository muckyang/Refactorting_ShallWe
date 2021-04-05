package ShallWe.Refactoring.entity.order.dto;

import ShallWe.Refactoring.entity.order.Category;
import ShallWe.Refactoring.entity.order.OrderStatus;
import ShallWe.Refactoring.entity.user.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private String title;
    private String description;
    private int goalPrice;
    private String category;
    private List<String> tags = new ArrayList<>();

    public OrderRequest(Long userId, String title, String description, int goalPrice, String category) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.goalPrice = goalPrice;
        this.category = category;
    }
}
