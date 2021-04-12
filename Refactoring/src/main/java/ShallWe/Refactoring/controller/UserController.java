package ShallWe.Refactoring.controller;

import ShallWe.Refactoring.entity.user.UserStatus;
import ShallWe.Refactoring.entity.user.dto.UserRequest;
import ShallWe.Refactoring.entity.user.dto.UserResponse;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.user.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final Logger logger;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @PostMapping("/users")
    @ApiOperation(value = "회원가입")
    public UserResponse joinUser(@RequestBody UserRequest request) throws Exception {
        User join = new User(request);
        userRepository.save(join);
        return new UserResponse(join);
    }

    //컨버팅 방식 자잘하게 사용가능 repository로 찾게 됨
    //조회 용도로만 사용해야하는 주의사항이 있다.
    @GetMapping("/users/{id}")
    @ApiOperation("회원 조회")
    public UserResponse getUser(@PathVariable("id") User user) {
        return new UserResponse(user);
    }

    @GetMapping("/checkNickname/{nickname}")
    @ApiOperation(value = "닉네임 중복 체크")
    public ResponseEntity<String> nicknameCheck(@PathVariable String nickname) {
        if (isAvailableNickname(nickname)) {
            logger.info("Available nickname.");
            return new ResponseEntity<>("Available nickname.", HttpStatus.OK);
        }
        logger.info("Nickname is already exists..");
        return new ResponseEntity<>("Nickname is already exists.", HttpStatus.OK);
    }

    private boolean isAvailableNickname(String nickname) {
        Optional<User> userOpt = userRepository.findUserByNickname(nickname);
        return userOpt.isEmpty();
    }

    @PutMapping("/users/{id}")
    @ApiOperation("회원 수정")
    public UserResponse UpdateUser(@PathVariable Long id,@RequestBody UserRequest request) {
        Optional<User> data = userRepository.findById(id);
        if (data.isPresent()) {
            User user = data.get();
            user.adapting(request);
            return new UserResponse(user);
        }
        return null;
    }

    @PutMapping("/user/ban/{id}")
    @ApiOperation("회원 밴")
    public String BanUser(@PathVariable Long id) {
        Optional<User> data = userRepository.findById(id);
        if (data.isPresent()) {
            data.get().getInfo().setUserStatus(UserStatus.BAN);
            return "ban success!";
        }
        return "ban fail!";
    }

    @PatchMapping("/user/active/{id}")
    @ApiOperation("회원 활성화")
    public String ActiveUser(@PathVariable Long id) {
        Optional<User> data = userRepository.findById(id);
        if (data.isPresent()) {
            data.get().getInfo().setUserStatus(UserStatus.ACTIVE);
            return "active success!";
        }
        return "active fail!";
    }

    @GetMapping("/users/paging")
    @ApiOperation("유저 조회 페이징")
    public Page<UserResponse> getUserForPaging(Pageable pageable) {
        return userRepository.getUserPaging(pageable);
    }

    @GetMapping("/users/scroll")
    @ApiOperation("유저 조회 스크롤링")
    public Slice<UserResponse> getUSerForScroll(Pageable pageable) {
        return userRepository.getUserScroll(pageable);
    }

}
