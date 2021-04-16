package ShallWe.Refactoring.service;

import ShallWe.Refactoring.entity.order.Category;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.dto.OrderRequest;
import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order findOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        } else
            //TODO 예외 새로 만들것
            throw new NullPointerException("Order is null");
    }

    public Order createOrder(OrderRequest request, User user) throws IllegalStateException {

        Order order = Order.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .endTime(request.getEndTime())
                .category(Category.valueOf(request.getCategory().toUpperCase()))
                .goalPrice(request.getGoalPrice())
                .build();
        return orderRepository.save(order);

    }

    public List<OrderResponse> getAll() {
        List<OrderResponse> result = new ArrayList<>();
        for (Order order : orderRepository.findAll())
            result.add(new OrderResponse(order));
        return result;
    }

    public List<OrderResponse> searchByTag(String tagName) {
        return orderRepository.searchByTagName(tagName);
    }

    public List<OrderResponse> searchByUserId(User user) {
        return orderRepository.searchByUser(user);
    }

    public Page<OrderResponse> findByPaging(Pageable pageable) {
        return orderRepository.getOrderPaging(pageable);
    }

}
