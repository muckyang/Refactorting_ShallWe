package refactoring.shallwe.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import refactoring.shallwe.entity.user.User;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom{

    public UserRepositoryImpl() {
        super(User.class);
    }

    //overrides~

}
