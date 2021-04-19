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
    @DisplayName("회원 생성 및 조회")
    public void userSave() throws Exception {
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

        userRepository.save(user);
        userRepository.findAll().forEach(System.out::println);

        assertThat(user).isEqualTo(userRepository.getOne(user.getId()));

    }

    @Test
    @DisplayName("전체 삭제")
    public void deleteAll() throws Exception {
        userRepository.deleteAll();
        assertThat(0).isEqualTo(userRepository.findAll().size());
    }

}
