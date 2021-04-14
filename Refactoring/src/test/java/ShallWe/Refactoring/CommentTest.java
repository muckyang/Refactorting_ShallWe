package ShallWe.Refactoring;

import ShallWe.Refactoring.entity.comment.Comment;
import ShallWe.Refactoring.entity.comment.CommentStatus;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.comment.CommentRepository;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
                .content("댓글 작성합니다")
                .status(CommentStatus.NORMAL)
                .build();

        commentRepository.save(comment);
//        assert(comment);
    }
}
