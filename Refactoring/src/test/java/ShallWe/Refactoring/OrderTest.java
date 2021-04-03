package ShallWe.Refactoring;

import ShallWe.Refactoring.entity.order.Category;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.OrderStatus;
import ShallWe.Refactoring.entity.partyMember.PartyMember;
import ShallWe.Refactoring.entity.partyMember.PartyStatus;
import ShallWe.Refactoring.entity.tag.Tag;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
public class OrderTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void createEM() {
        logger.trace("*************** Order Test Start *******************");
    }

    @Test
    public void getOrderPageable() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10,
                Sort.by(Sort.Direction.DESC, "order_id"));
    }


    @Test
    public void saveOrder() {
        Optional<User> opUser = userRepository.findById(1L);
        if (opUser.isEmpty()) {
            fail();
            return;
        }
        User user = opUser.get();
        PartyMember partyMember = new PartyMember();
        partyMember.setMember(user);
        partyMember.setPrice(6000);
        partyMember.setJoinDescription("안녕하세요");
        partyMember.setStatus(PartyStatus.JOIN);

        Order order = new Order();
        order.setUser(user);
        order.setGoalPrice(30000);
        order.setCategory(Category.SHARE);
        order.setStatus(OrderStatus.WAITING);
        order.setEndTime(LocalDateTime.now().plusHours(4L));
        order.setDescription("물건 같이사요");
        order.setTitle("물건 같이 구매하실 분 찾습니다.");
        em.persist(order);
        partyMember.setOrder(order);
        em.persist(partyMember);

        //태그 생략함
    }


    public List<Tag> getTagList(List<String> tagList) {
        List<Tag> result = new ArrayList<>();
        for (String tagName : tagList) { //TODO Query DSL 을 써야할 것 같다. 한번씩 날리지 말고 한번에 다 날리면 됨.
            result.add(StringToTag(tagName));
        }
        return result;
    }

    public Tag StringToTag(String name) {
        Tag tag = null;


        return null;

    }

    @Test
    public void fetchTest() throws Exception {
        List<Order> result = orderRepository.findEntityGraphAll();
        for(Order eachOrder : result){
            System.out.println(eachOrder.toString());
        }
        System.out.println(result.size());
    }
}
