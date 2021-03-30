package ShallWe.Refactoring;

import ShallWe.Refactoring.entity.address.Address;
import ShallWe.Refactoring.entity.user.Info;
import ShallWe.Refactoring.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @BeforeEach
    void createEM() {
        logger.trace("*************** START USER TEST *******************");
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void init() {
        int userCnt = 10;//생성 인원 설정
        for (int i = 0; i < userCnt; i++) {
            createUser();
        }
        logger.info("User Initialize COMPLETED");
    }

    private void createUser() {
        String randomNum = (int) (Math.random() * 100)+"";
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

}
