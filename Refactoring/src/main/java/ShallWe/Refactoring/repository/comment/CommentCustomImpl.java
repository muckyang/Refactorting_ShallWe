package ShallWe.Refactoring.repository.comment;

import ShallWe.Refactoring.entity.comment.Comment;
import ShallWe.Refactoring.entity.comment.QComment;
import ShallWe.Refactoring.entity.order.QOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static ShallWe.Refactoring.entity.comment.QComment.*;
import static ShallWe.Refactoring.entity.order.QOrder.*;

public class CommentCustomImpl implements CommentCustom {

    private final JPAQueryFactory queryFactory;

    public CommentCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Comment findIdFetch(Comment target) {

        return queryFactory.selectFrom(comment)
                .join(comment.order, order).fetchJoin()
                .where(comment.eq(target))
                .fetchOne();
    }
}
