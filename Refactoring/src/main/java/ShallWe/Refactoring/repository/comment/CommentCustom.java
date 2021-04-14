package ShallWe.Refactoring.repository.comment;

import ShallWe.Refactoring.entity.comment.Comment;

public interface CommentCustom {
    Comment findIdFetch(Comment target);
}
