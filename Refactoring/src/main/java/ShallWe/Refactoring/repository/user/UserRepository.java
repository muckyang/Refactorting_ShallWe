package ShallWe.Refactoring.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ShallWe.Refactoring.entity.user.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends UserRepositoryCustom, JpaRepository<User,Long> {
    Optional<User> findUserByNickname(String nickname);

    Page<User> findPageByPassword(String password, Pageable pageable);//count query O
    Slice<User> findSliceByNameContaining(String name, Pageable pageable); // count query X,
    // limit +1 infinite scrolling, more view 구현

    @EntityGraph(attributePaths = {"orders"})
    @Query("select u from User u")
    List<User> findEntityGraphAll();

    @Query("select u from User u join fetch u.orders")
    List<User> findFetchJoin();

}
