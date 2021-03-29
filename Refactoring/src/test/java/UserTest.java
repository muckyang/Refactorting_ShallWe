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

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    void createEM(){
        logger.trace("*************** START USER TEST *******************");
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    @Transactional
    @Rollback(false)
    public void userCreate(){
        User user1 =  makeUser1();
        em.persist(user1);
        assertThat(em.find(User.class,user1.getId())).isEqualTo(user1);
        logger.info("COMPLETE");

    }

    private User makeUser1() {
        //test
        User user =new User();
        user.setEmail("asdfgbve@never.com");
        user.setPassword("12341234");
        user.setNickname("doper2");
        user.setName("KIM YOUNG NAM");
        user.setAddress(new Address("seoul","41st","601"));
        Info info = createInfo();
        user.setInfo(info);
        return user;
    }

    public Info createInfo(){
        Info newInfo = new Info(1993,6,17);
        newInfo.setPoint(1000);//디폴트
        return newInfo;
    }
}
