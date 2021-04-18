package ShallWe.Refactoring.ControllerTest;

import ShallWe.Refactoring.controller.OrderController;
import ShallWe.Refactoring.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    OrderService orderService;
    @MockBean
    UserService userService;
    @MockBean
    TagService tagService;
    @MockBean
    PartyMemberService partyMemberService;
    @MockBean
    ValidationService validationService;

    @BeforeEach
    public void before() {

    }

    @Test
    public void createOrder() {

    }
    @Test
    public void getOrder() {

    }


}
