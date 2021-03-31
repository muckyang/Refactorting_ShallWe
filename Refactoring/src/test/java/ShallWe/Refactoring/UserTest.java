package ShallWe.Refactoring;

import ShallWe.Refactoring.dto.user.UserResponse;
import ShallWe.Refactoring.entity.address.Address;
import ShallWe.Refactoring.entity.user.Info;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
public class UserTest {

    @Autowired
    private EntityManager em;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void createEM() {
        logger.trace("*************** START USER TEST *******************");
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    @Transactional
    public void findAll() throws Exception {
        List<UserResponse> responses = userRepository.findUserAll();
        for(UserResponse res : responses){
            System.out.println(res.toString());
        }
    }


    @Test
    public void init() {
        int userCnt = 100;//생성 인원 설정
        for (int i = 0; i < userCnt; i++) {
            createUser(i);
        }
        logger.info("User Initialize COMPLETED");
    }

    private void createUser(int num) {
        String randomNum = (int) (Math.random() * 1000)+(num*1000)+"";
        User user = new User();
        user.setEmail("user"+randomNum+"@never.com");
        user.setPassword("12341234");
        user.setNickname("nickname"+randomNum);
        user.setName("Clone"+randomNum);
        user.setAddress("seoul", randomNum+"street", "room 1"+randomNum);
        user.setInfo(createInfo());
        em.persist(user);
    }


    public Info createInfo() {
        int year = (int) (Math.random() * 30) + 1990;
        int month = (int) (Math.random() * 12) + 1;
        int day = (int) (Math.random() * 28) + 1;
        return new Info(year, month, day);
    }


    @Test
    @Transactional
    public void getUserSlicePage() throws Exception {
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"user_id"));
        Page<UserResponse> responses = userRepository.findByPoint(1000,pageRequest);
//        Slice<UserResponse> responses = userRepository.findByPoint(1000);
        for(UserResponse res : responses){
            System.out.println(res.toString());
        }
    }

}
