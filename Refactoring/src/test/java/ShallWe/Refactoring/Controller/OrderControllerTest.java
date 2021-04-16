package ShallWe.Refactoring.Controller;

import ShallWe.Refactoring.controller.OrderController;
import ShallWe.Refactoring.controller.UserController;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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


}
