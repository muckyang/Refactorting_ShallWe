package ShallWe.Refactoring.LegacyTest;

import ShallWe.Refactoring.entity.comment.Comment;
import ShallWe.Refactoring.entity.comment.CommentStatus;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.comment.CommentRepository;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.user.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest(
    properties = {"spring.config.location=classpath:application-test.yml"}
)
@Transactional
public class CommentTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(false)
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
