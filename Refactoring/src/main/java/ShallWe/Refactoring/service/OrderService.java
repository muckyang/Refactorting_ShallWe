package ShallWe.Refactoring.service;

import ShallWe.Refactoring.entity.order.Category;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.dto.OrderRequest;
import ShallWe.Refactoring.entity.order.dto.OrderRequestBuilder;
import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    public Order createOrder(OrderRequest request, User user) {
            Order order = orderRepository.save(new Order(request, user));
            order.setCategory(Category.valueOf(request.getCategory().toUpperCase()));
            orderRepository.save(order);
            return order;

    }


    public List<OrderResponse> searchByTag(String name) {
        return orderRepository.searchByTagName(name);
    }


    public List<OrderResponse> searchByUserId(Long userId) {
        User findUser = userRepository.findById(userId).get();

        return orderRepository.searchByUser(findUser);
    }

}
