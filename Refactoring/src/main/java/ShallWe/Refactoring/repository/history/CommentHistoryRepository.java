package ShallWe.Refactoring.repository.history;

import ShallWe.Refactoring.entity.comment.CommentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentHistoryRepository extends CommentHistoryCustom, JpaRepository<CommentHistory,Long> {

}
