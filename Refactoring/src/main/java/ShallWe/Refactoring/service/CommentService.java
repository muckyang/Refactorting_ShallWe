package ShallWe.Refactoring.service;

import ShallWe.Refactoring.entity.comment.Comment;
import ShallWe.Refactoring.entity.comment.CommentHistory;
import ShallWe.Refactoring.entity.comment.CommentStatus;
import ShallWe.Refactoring.entity.comment.dto.CommentResponse;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.comment.CommentRepository;
import ShallWe.Refactoring.repository.history.CommentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentHistoryRepository historyRepository;

    public CommentResponse createComment(Order order, User user, String content) {
        Comment comment = Comment.builder()
                .order(order)
                .user(user)
                .content(content)
                .status(CommentStatus.NORMAL)
                .build();
        order.addComment(comment);

        commentRepository.save(comment);

        return new CommentResponse(comment);
    }

    public CommentResponse findById(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            return new CommentResponse(comment.get());
        }
        throw new NullPointerException("comment not exist");
    }

    public void patchText(Long commentId, String updateText) {
        Comment comment = commentRepository.getOne(commentId);
        Comment target = commentRepository.findIdFetch(comment);
        historyRepository.save(new CommentHistory(target));
        target.update(updateText);

    }
}
