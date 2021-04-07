package ShallWe.Refactoring;

import ShallWe.Refactoring.entity.order.Category;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.dto.OrderRequest;
import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.partyMember.PartyMember;
import ShallWe.Refactoring.entity.partyMember.PartyStatus;
import ShallWe.Refactoring.entity.tag.Tag;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.user.UserRepository;
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
    public void getOrderPageable() {
        PageRequest pageRequest = PageRequest.of(0, 10,
                Sort.by(Sort.Direction.DESC, "order_id"));
    }

    @Test
    public void saveOrder() {

        List<String> tags  = new ArrayList<>();
        tags.add("밀키트");
        tags.add("음식");

        OrderRequest request = new OrderRequest.Builder(1L)
                .setTitle("제목")
                .setDescription("내용")
                .setGoalPrice(30000)
                .setCategory("Category")
                .setTags(tags)
                .setEndTime(LocalDateTime.now().plusHours(4L))
                .build();

        Optional<User> opUser = userRepository.findById(request.getUserId());
        if (opUser.isEmpty()) {
            fail();
            return;
        }

        User user = opUser.get();

        //TODO 카테고리 mapping

        PartyMember partyMember = new PartyMember();
        partyMember.setMember(user);
        partyMember.setPrice(6000);
        partyMember.setStatus(PartyStatus.JOIN);

        Order order = new Order(request,user);
        order.setCategory(Category.SHARE);
        order.setEndTime(LocalDateTime.now().plusHours(4L));

        for(String tagName : tags){
            Tag tag =new Tag(tagName);
            tag.setOrder(order);
            em.persist(tag);
        }

        em.persist(order);
        partyMember.setOrder(order);
        em.persist(partyMember);
        logger.info("complete");
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
