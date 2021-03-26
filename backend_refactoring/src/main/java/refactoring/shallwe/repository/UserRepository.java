package refactoring.shallwe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refactoring.shallwe.entity.user.User;

public interface UserRepository extends JpaRepository<User,Long> , UserRepositoryCustom {
}
