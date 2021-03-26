package ShallWe.Refactoring.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import ShallWe.Refactoring.entity.user.User;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom{

    public UserRepositoryImpl() {
        super(User.class);
    }

    //overrides~

}
