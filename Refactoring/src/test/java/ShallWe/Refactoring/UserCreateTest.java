package ShallWe.Refactoring;

import ShallWe.Refactoring.entity.address.Address;
import ShallWe.Refactoring.entity.user.dto.UserResponse;
import ShallWe.Refactoring.entity.user.Info;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
public class UserCreateTest {

    @Autowired
    private EntityManager em;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void before() {
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
        String name = "Clone"+randomNum;
        String email = "user" + randomNum+ "@naver.com";
        String password = "12341234";


        User user = User.builder(name,email,password)
                .nickname("nick" + randomNum)
                .address(Address.builder("seoul",randomNum+"street","room 1"+randomNum).build())
                .info(new Info(2021,3,13))
                .build();
        em.persist(user);
    }

    public Info createInfo() {
        int year = (int) (Math.random() * 30) + 1990;
        int month = (int) (Math.random() * 12) + 1;
        int day = (int) (Math.random() * 28) + 1;
        return new Info(year, month, day);
    }

    @Test
    @Rollback
    public void fetchTest() throws Exception {
        List<User> result = userRepository.findEntityGraphAll();
        for(User eachUser : result){
            System.out.println(eachUser.toString());
            if(eachUser.getOrders().size()>0)
                System.out.println("생성한 order 가 있는 유저");
        }
        System.out.println(result.size());
    }
}
