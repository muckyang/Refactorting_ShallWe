package ShallWe.Refactoring.repository.user;

import ShallWe.Refactoring.dto.user.QUserResponse;
import ShallWe.Refactoring.dto.user.UserResponse;
import ShallWe.Refactoring.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static ShallWe.Refactoring.entity.user.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<UserResponse> findAll() {
       return queryFactory
                .select(new QUserResponse(user))
                .from(user)
                .fetch();
    }

}
