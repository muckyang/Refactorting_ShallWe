package ShallWe.Refactoring;

import ShallWe.Refactoring.entity.order.Category;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.OrderStatus;
import ShallWe.Refactoring.entity.order.dto.OrderRequest;
import ShallWe.Refactoring.entity.order.dto.OrderRequestBuilder;
import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.partyMember.PartyMember;
import ShallWe.Refactoring.entity.partyMember.PartyStatus;
import ShallWe.Refactoring.entity.tag.Tag;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.partyMember.PartyMemberRepository;
import ShallWe.Refactoring.repository.tag.TagRepository;
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
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PartyMemberRepository partyMemberRepository;

    @BeforeEach
    public void createEM() {
        logger.trace("*************** Order Test Start *******************");
    }

    @Test
    public void getOrderPageable() {
        PageRequest pageRequest = PageRequest.of(0, 10,
                Sort.by(Sort.Direction.DESC, "order_id"));
    }

    @Test
    public void saveOrder() {

        List<String> tags = new ArrayList<>();
        tags.add("치킨");

        OrderRequest request = new OrderRequest();
        request.setUserId(1L);
        request.setTitle("치킨조아");
        request.setDescription("치킨 같이 시켜먹어요!");
        request.setGoalPrice(32000);
        request.setCategory(Category.DELIVERY.toString());
        request.setTags(tags);
        request.setEndTime(LocalDateTime.now().plusHours(4L));

        User user = getUser(request.getUserId());

        Order order = Order.builder(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .endTime(request.getEndTime())
                .goalPrice(request.getGoalPrice())
                .category(Category.DELIVERY)
                .status(OrderStatus.WAITING)
                .build();


        logger.info(order.toString());
        orderRepository.save(order);
        logger.info("before tag Save");

        List<String> tagList = request.getTags();
        for (String tagName : tagList) {
            Tag tag = new Tag(tagName);
            tag.setOrder(order);
            logger.info("order use -> order insert");
            tagRepository.save(tag);
        }

        logger.info("before Party New");
        PartyMember partyMember = new PartyMember();

        partyMember.setMember(user);
        partyMember.setPrice(6000);
        partyMember.setStatus(PartyStatus.JOIN);
        partyMember.setOrder(order);

        logger.info("before Party Save");
        partyMemberRepository.save(partyMember);

        logger.info("complete");
    }

    @Test
    public void fetchTest() throws Exception {
        List<Order> result = orderRepository.findEntityGraphAll();
        for (Order eachOrder : result) {
            System.out.println(eachOrder.toString());
        }
        System.out.println(result.size());
    }

    @Test
    public void orderPageTest() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<OrderResponse> result = orderRepository.getOrderPaging(pageable);
        for (OrderResponse or : result.getContent()) {
            System.out.println(or.toString());
        }
    }

    public User getUser(Long id) {
        Optional<User> opUser = userRepository.findById(id);
        if (opUser.isEmpty()) {
            fail();
            return null;
        }
        return opUser.get();
    }

    @Test
    public void EnumTest() throws Exception {
        //then
        Category status = Category.valueOf("SHARE");
        System.out.println(status);
    }

}
