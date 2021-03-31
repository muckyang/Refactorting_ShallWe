package ShallWe.Refactoring.repository.user;

import ShallWe.Refactoring.dto.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import ShallWe.Refactoring.entity.user.User;

import java.util.Optional;

public interface UserRepository extends UserRepositoryCustom, JpaRepository<User,Long> {
    Optional<User> findUserByNickname(String nickname);
//    Page<User> findByName(String name, Pageable pageable);//count query O

    //    내부적으로 limit +1 로 인피니티 스크롤링, 더 보기 구현가능
//    Slice<User> findByName(String name, Pageable pageable); // count query X,


    Page<UserResponse> findByPoint(int point,Pageable pageable);




}
