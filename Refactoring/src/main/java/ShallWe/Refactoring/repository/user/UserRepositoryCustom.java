package ShallWe.Refactoring.repository.user;

import ShallWe.Refactoring.dto.user.UserResponse;

import java.util.List;

public interface UserRepositoryCustom {
    public List<UserResponse> findUserAll();
}

