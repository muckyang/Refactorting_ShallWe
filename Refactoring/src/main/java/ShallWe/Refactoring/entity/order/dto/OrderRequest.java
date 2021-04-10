package ShallWe.Refactoring.entity.order.dto;

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

    private LocalDateTime endTime;
    private int pay;
}
