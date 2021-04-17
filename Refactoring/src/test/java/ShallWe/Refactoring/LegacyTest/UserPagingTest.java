package ShallWe.Refactoring.LegacyTest;

import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.entity.user.dto.UserResponse;
import ShallWe.Refactoring.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest(
        properties = {"spring.config.location=classpath:application-test.yml"}
)
@Transactional
public class UserPagingTest {

    @Autowired
    private EntityManager em;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void before() {
        logger.trace("*************** START PAGING TEST *******************");
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    @DisplayName("유저 페이징")
    public void getUserPaging() {
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"id"));
        Page<User> page = userRepository.findPageByPassword("12341234",pageRequest);
        Page<UserResponse> responses = page.map(UserResponse::new);

        List<UserResponse> content = responses.getContent(); // 내부 내용 가져옴

        for(UserResponse res : content){
            System.out.println(res.toString());
        }
        System.out.println(content.size());
    }


    @Test
    @DisplayName("유저 슬라이스")
    public void getUserSlice() throws Exception {
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"id"));
        Slice<User> slice = userRepository.findSliceByNameContaining("Clone",pageRequest);
        // 람다식 자동 적용 slice.map(user -> new UserResponse(user));
        Slice<UserResponse> responses= slice.map(UserResponse::new);
        List<UserResponse> content = responses.getContent(); // 내부 내용 가져옴
        for(UserResponse res : content){
            System.out.println(res.toString());
        }

        System.out.println(content.size());
    }

}
