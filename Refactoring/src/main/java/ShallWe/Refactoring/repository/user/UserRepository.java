package ShallWe.Refactoring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ShallWe.Refactoring.entity.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> , UserRepositoryCustom {
    Optional<User> findUserByNickname(String nickname);
}
