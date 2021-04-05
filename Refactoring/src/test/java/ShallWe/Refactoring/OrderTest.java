package ShallWe.Refactoring;

import ShallWe.Refactoring.entity.order.Category;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.OrderStatus;
import ShallWe.Refactoring.entity.order.dto.OrderRequest;
import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.partyMember.PartyMember;
import ShallWe.Refactoring.entity.partyMember.PartyStatus;
import ShallWe.Refactoring.entity.tag.Tag;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.category.CategoryRepository;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void createEM() {
        logger.trace("*************** Order Test Start *******************");
    }

    @Test
    public void getOrderPageable() {
        PageRequest pageRequest = PageRequest.of(0, 10,
                Sort.by(Sort.Direction.DESC, "order_id"));
    }

    @Test
    public void saveOrder() {

        OrderRequest request = new OrderRequest(1L,"제목","내용",30000,"Category");
        List<String > tags  = new ArrayList<>();
        tags.add("밀키트");
        tags.add("음식");
        request.setTags(tags);

        Optional<User> opUser = userRepository.findById(request.getUserId());
        if (opUser.isEmpty()) {
            fail();
            return;
        }
        //TODO 카테고리 존재여부 확인

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

        for(String tagName : tags){
            Tag tag =new Tag(tagName);
            tag.setOrder(order);
            em.persist(tag);
        }

        em.persist(order);
        partyMember.setOrder(order);
        em.persist(partyMember);

    }

    @Test
    public void fetchTest() throws Exception {
        List<Order> result = orderRepository.findEntityGraphAll();
        for(Order eachOrder : result){
            System.out.println(eachOrder.toString());
        }
        System.out.println(result.size());
    }

    @Test
    public void orderPageTest(){
        Pageable pageable = PageRequest.of(0,1);
        Page<OrderResponse> result = orderRepository.getUserPaging(pageable);
        for(OrderResponse or :result.getContent()){
            System.out.println(or.toString());
        }
    }

}
