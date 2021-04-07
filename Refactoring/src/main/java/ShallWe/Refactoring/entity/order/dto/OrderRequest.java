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

    public static class Builder {
        private final Long userId;
        private String title;
        private String description;
        private int goalPrice;
        private String category;
        private List<String> tags;
        private LocalDateTime endTime;

        public Builder(Long userId) {
            this.userId = userId;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setGoalPrice(int goalPrice) {
            this.goalPrice = goalPrice;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setTags(List<String> tags) {
            this.tags = new ArrayList<>();
            this.tags.addAll(tags);
            return this;
        }

        public Builder setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public OrderRequest build() {
            return new OrderRequest(this);
        }
    }


    private OrderRequest(Builder builder) {
        this.userId = builder.userId;
        this.title = builder.title;
        this.description = builder.description;
        this.goalPrice = builder.goalPrice;
        this.category = builder.category;
        this.endTime = builder.endTime;
        this.tags = builder.tags;
    }
}
