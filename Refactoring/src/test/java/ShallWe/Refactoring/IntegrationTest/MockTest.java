package ShallWe.Refactoring.IntegrationTest;

import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.user.dto.UserResponse;
import ShallWe.Refactoring.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.WebApplicationContext;


import static org.assertj.core.api.BDDAssertions.then;


@SpringBootTest(
        properties = {"spring.config.location=classpath:application-test.yml"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class MockTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext ctx;


    @BeforeEach
    public void before() {
        log.info("##### USER GET 테스트 #####");
        log.info("******** START : MOC MVC test **********");
    }

    @AfterEach
    public void after() {
        log.info("******** END : MOC MVC test **********");
    }

    @Test
    public void AllTest() throws Exception {
        getMember(Long.parseLong("10"));
        getMember(Long.parseLong("20"));
        getMemberRes(Long.parseLong("1"));
        getMemberRes(Long.parseLong("2"));
    }

    public void getMember(Long userId) throws Exception {
        mvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name", is("Clone9696")))
                .andDo(print());
    }

    public void getMemberRes(Long userId) throws Exception {
        ResponseEntity<UserResponse> response =
                restTemplate.getForEntity("/api/users/" + userId, UserResponse.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isNotNull();

    }
    @Test
    public void getAllOrders() throws Exception {
        ResponseEntity<OrderResponse[]> response =
                restTemplate.getForEntity("/api/order-all", OrderResponse[].class);
        for( OrderResponse res : response.getBody()){
            System.out.println(res.toString());
        }
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isNotNull();

    }

}
