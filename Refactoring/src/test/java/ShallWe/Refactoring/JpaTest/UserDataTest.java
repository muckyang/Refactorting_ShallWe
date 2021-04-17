package ShallWe.Refactoring.JpaTest;

import ShallWe.Refactoring.entity.address.Address;
import ShallWe.Refactoring.entity.user.Info;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest(properties = {"spring.config.location=classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDataTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 저장")
    public void userSave() throws Exception {
        //given
        User user = User.builder()
                .name("Joe")
                .password("12341234")
                .email("12341234")
                .nickname("nickname")
                .address(Address.builder()
                        .city("12341234")
                        .street("12341234")
                        .detail("12341234")
                        .build())
                .info(new Info(2012, 5, 12))
                .build();
        System.out.println(user.toString());
        //when 오류시점
        userRepository.save(user);
        userRepository.findAll().forEach(System.out::println);
        //then
        assertThat(user).isEqualTo(userRepository.getOne(user.getId()));

    }


}
