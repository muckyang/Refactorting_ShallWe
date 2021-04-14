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
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.partyMember.PartyMemberRepository;
import ShallWe.Refactoring.repository.tag.TagRepository;
import ShallWe.Refactoring.repository.user.UserRepository;
import ShallWe.Refactoring.service.UserService;
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

@SpringBootTest(
        properties = {"spring.config.location=classpath:application-test.yml"}
)
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
    @Autowired
    private UserService userService;

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

        OrderRequest request = OrderRequest.builder()
                .userId(1L)
                .title("치킨먹을사람~")
                .description("치킨 같이 시켜 먹어요!")
                .tags(tags)
                .category(Category.DELIVERY.toString())
                .goalPrice(32000)
                .endTime(LocalDateTime.now().plusHours(4L))
                .build();

        User user = userService.findUser(request.getUserId());

        Order order = Order.builder()
                .user(user)
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
        PartyMember partyMember = PartyMember.builder()
                .user(user)
                .order(order)
                .price(6000)
                .status(PartyStatus.JOIN)
                .joinDescription("글 게시자 본인 입니다.")
                .build();

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

    @Test
    public void EnumTest() throws Exception {
        //then
        Category status = Category.valueOf("SHARE");
        System.out.println(status);
    }

}
