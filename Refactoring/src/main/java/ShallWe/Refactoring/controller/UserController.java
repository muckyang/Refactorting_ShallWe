package ShallWe.Refactoring.controller;

import ShallWe.Refactoring.dto.user.UserRequest;
import ShallWe.Refactoring.dto.user.UserResponse;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.user.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PostMapping("/joinUser")
    @ApiOperation(value = "User Join")
    public @ResponseBody UserResponse joinUser(@RequestBody UserRequest request) throws Exception {
        User join = new User(request);
        userRepository.save(join);
        return new UserResponse(join);
    }

    @GetMapping("/user/{nickname}")
    @ApiOperation(value = "Nickname Reduplication Check")
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
    @PutMapping("/user")
    @ApiOperation("User Update")
    public ResponseEntity<String> updateUser(@RequestBody UserRequest request){
        if(isAvailableNickname(request.getNickname())){
//            userRepository.updateUser();
            return new ResponseEntity<>("Update Completed.",HttpStatus.OK);
        }
        return new ResponseEntity<>("Update failed.",HttpStatus.OK);

    }

}
