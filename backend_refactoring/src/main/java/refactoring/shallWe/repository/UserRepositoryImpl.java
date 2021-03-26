package refactoring.shallWe.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import refactoring.shallWe.entity.user.User;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom{

    public UserRepositoryImpl() {
        super(User.class);
    }

    //overrides~

}
