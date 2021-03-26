package ShallWe.Refactoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ShallWe.Refactoring.entity.user.User;

public interface UserRepository extends JpaRepository<User,Long> , UserRepositoryCustom {
}
