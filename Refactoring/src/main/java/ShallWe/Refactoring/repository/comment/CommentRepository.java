package ShallWe.Refactoring.repository.comment;

import ShallWe.Refactoring.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends CommentCustom, JpaRepository<Comment,Long> {

}
