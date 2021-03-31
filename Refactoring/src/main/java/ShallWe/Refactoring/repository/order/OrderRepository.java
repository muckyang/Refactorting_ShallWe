package ShallWe.Refactoring.repository.order;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

}
