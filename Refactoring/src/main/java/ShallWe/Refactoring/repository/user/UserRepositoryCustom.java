package ShallWe.Refactoring.repository.user;

import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.entity.user.dto.UserRequest;
import ShallWe.Refactoring.entity.user.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserResponse> findUserAll();

    Page<UserResponse> getUserPaging(Pageable pageable);
    Slice<UserResponse> getUserScroll(Pageable pageable);
//    public Page<UserResponse> findByInfoPoint(int point, Pageable pageable);

}

