package ShallWe.Refactoring.service;

import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new NullPointerException("Order is null");
    }
}
