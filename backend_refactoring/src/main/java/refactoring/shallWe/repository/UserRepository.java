package refactoring.shallWe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refactoring.shallWe.entity.user.User;

public interface UserRepository extends JpaRepository<User,Long> , UserRepositoryCustom {
}
