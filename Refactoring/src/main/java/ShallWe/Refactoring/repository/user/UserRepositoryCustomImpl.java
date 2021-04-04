package ShallWe.Refactoring.repository.user;

import ShallWe.Refactoring.entity.user.QUser;
import ShallWe.Refactoring.entity.user.dto.QUserResponse;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.entity.user.dto.UserRequest;
import ShallWe.Refactoring.entity.user.dto.UserResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static ShallWe.Refactoring.entity.user.QUser.user;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<UserResponse> findUserAll() {
        return queryFactory
                .select(new QUserResponse(user))
                .from(user)
                .fetch();
    }

    public UserResponse updateUser(UserRequest request) {
        User result = queryFactory.selectFrom(user).where(user.id.eq(request.getId())).fetchOne();
        queryFactory
                .update(user).where(user.id.eq(request.getId()))
                .set(user, result)
                .execute();
        return new UserResponse(result);
    }

    @Override
    public Page<UserResponse> getUserPaging(Pageable pageable) {
        QueryResults<User> result = queryFactory
                .selectFrom(user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<UserResponse> content = new ArrayList<>();
        for (User eachUser : result.getResults()) {
            content.add(new UserResponse(eachUser));
        }
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Slice<UserResponse> getUserScroll(Pageable pageable) {
        QueryResults<User> result = queryFactory
                .selectFrom(user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetchResults();

        List<UserResponse> content = new ArrayList<>();
        for (User eachUser : result.getResults()) {
            content.add(new UserResponse(eachUser));
        }

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

}
