package ShallWe.Refactoring.Testinitialize;


import ShallWe.Refactoring.entity.address.Address;
import ShallWe.Refactoring.entity.comment.Comment;
import ShallWe.Refactoring.entity.comment.CommentStatus;
import ShallWe.Refactoring.entity.order.Category;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.OrderStatus;
import ShallWe.Refactoring.entity.order.dto.OrderRequest;
import ShallWe.Refactoring.entity.partyMember.PartyMember;
import ShallWe.Refactoring.entity.partyMember.PartyStatus;
import ShallWe.Refactoring.entity.tag.Tag;
import ShallWe.Refactoring.entity.user.Info;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.comment.CommentRepository;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.partyMember.PartyMemberRepository;
import ShallWe.Refactoring.repository.tag.TagRepository;
import ShallWe.Refactoring.repository.user.UserRepository;
import ShallWe.Refactoring.service.UserService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        properties = {"spring.config.location=classpath:application-init.yml"}
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Rollback(false)
public class TestDBInit {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PartyMemberRepository partyMemberRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void before() {
        logger.trace("*************** Start Test DB Init *******************");
    }


    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("유저 데이터 생성")
    public void init() {
        int userCnt = 100;//생성 인원 설정
        for (int i = 0; i < userCnt; i++) {
            createUser(i);
        }
        logger.info("User Initialize COMPLETED");
    }

    private void createUser(int num) {
        String randomNum = (int) (Math.random() * 1000) + (num * 1000) + "";
        String name = "Clone" + randomNum;
        String email = "user" + randomNum + "@naver.com";
        String password = "12341234";
        String nickname = "nick" + randomNum;
        String city = "seoul";
        String street = randomNum + "street";
        String detail ="room 1" + randomNum;
        int year = (int) (Math.random() * 30) + 1990;
        int month = (int) (Math.random() * 12) + 1;
        int day = (int) (Math.random() * 28) + 1;
        User user = User.builder()
                .name(name)
                .password(password)
                .email(email)
                .nickname(nickname)
                .address(Address.builder()
                        .city(city)
                        .street(street)
                        .detail(detail)
                        .build())
                .info(new Info(year, month, day))
                .build();
        em.persist(user);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("주문 데이터 생성")
    public void saveOrder() {
        List<String> tags = new ArrayList<>();
        tags.add("치킨");
        tags.add("음식");

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
    @org.junit.jupiter.api.Order(3)
    @DisplayName("댓글 등록")
    public void create() throws Exception {
        Order order = orderRepository.getOne(1L);
        User user = userRepository.getOne(2L);
        Comment comment = Comment.builder()
                .order(order)
                .user(user)
                .content("댓글 작성 합니다")
                .status(CommentStatus.NORMAL)
                .build();

        commentRepository.save(comment);
        order.addComment(comment);
        assertThat(comment).isEqualTo(commentRepository.getOne(comment.getId()));

    }

}
