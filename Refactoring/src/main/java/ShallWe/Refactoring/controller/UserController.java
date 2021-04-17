package ShallWe.Refactoring.controller;

import ShallWe.Refactoring.entity.user.UserStatus;
import ShallWe.Refactoring.entity.user.dto.UserRequest;
import ShallWe.Refactoring.entity.user.dto.UserResponse;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.exception.DuplicationNicknameException;
import ShallWe.Refactoring.repository.user.UserRepository;
import ShallWe.Refactoring.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    @PostMapping("/users")
    @ApiOperation(value = "회원가입")
    public UserResponse joinUser(@RequestBody UserRequest request) throws Exception {
        return userService.save(request);
    }

    @GetMapping("/users/{id}")
    @ApiOperation("회원 조회")
    public UserResponse getUser(@PathVariable("id") Long userId) {
        User user = userService.findUser(userId);
        return new UserResponse(user);
    }
    //컨버팅 방식 자잘하게 사용가능 repository로 찾게 됨
    //조회 용도로만 사용해야하는 주의사항이 있다.
    //    @GetMapping("/users/{id}")
//    @ApiOperation("회원 조회")
//    public UserResponse getUser(@PathVariable("id") User user) {
//        return new UserResponse(user);
//    }
    @GetMapping("/user-all")
    @ApiOperation("전체 회원 조회")
    public List<UserResponse> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/checkNickname/{nickname}")
    @ApiOperation(value = "닉네임 중복 체크")
    public ResponseEntity<String> nicknameCheck(@PathVariable String nickname) throws Exception {
        if(userService.canUseNickname(nickname)) {
            return new ResponseEntity<>("Available nickname.", HttpStatus.OK);
        }else
            return new ResponseEntity<>("Duplication nickname" , HttpStatus.OK);
    }

    @GetMapping("/users/paging")
    @ApiOperation("유저 조회 페이징")
    public Page<UserResponse> getUserPaging(Pageable pageable) {
        return userService.getUserPage(pageable);
    }

    @GetMapping("/users/scroll")
    @ApiOperation("유저 조회 스크롤링")
    public Slice<UserResponse> getUserScroll(Pageable pageable) {
        return userService.getUserScroll(pageable);
    }

//    @PutMapping("/users/{id}")
//    @ApiOperation("회원 수정")
//    public UserResponse updateUser(@PathVariable Long id,@RequestBody UserRequest request) {
//        Optional<User> data = userRepository.findById(id);
//        if (data.isPresent()) {
//            User user = data.get();
//            //update logic Service에 요청할것
//            return new UserResponse(user);
//        }
//        return null;
//    }


    //관리자 기능
    @PutMapping("/user/ban/{id}")
    @ApiOperation("회원 밴")
    public String banUser(@PathVariable Long id) {
        userService.banUser(id);
        return "ban success!";
    }

    @PatchMapping("/user/active/{id}")
    @ApiOperation("회원 활성화")
    public String activeUser(@PathVariable Long id) {
        userService.activeUser(id);
        return "ban success!";
    }



}


