package ShallWe.Refactoring.service;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.dto.OrderRequest;
import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.tag.Tag;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.tag.TagRepository;
import ShallWe.Refactoring.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    public Order createOrder(OrderRequest request, User user) {
        Order order = orderRepository.save(new Order(request, user));
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
