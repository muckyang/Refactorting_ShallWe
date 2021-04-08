package ShallWe.Refactoring.repository.order;

import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.user.User;

import java.util.List;

public interface OrderSearchCustom {
    List<OrderResponse> searchByTagName(String tagName);
    List<OrderResponse> searchByUser(User user);
}
