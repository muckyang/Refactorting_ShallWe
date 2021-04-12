package ShallWe.Refactoring.controller;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.dto.OrderRequest;
import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.service.OrderService;
import ShallWe.Refactoring.service.PartyMemberService;
import ShallWe.Refactoring.service.TagService;
import ShallWe.Refactoring.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @Autowired
    PartyMemberService partyMemberService;

    @PostMapping("/orders/create")
    @ApiOperation("Order Create")
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        User user = userService.findUser(request.getUserId());
        Order order = orderService.createOrder(request, user);
        tagService.createTags(order, request.getTags());
        partyMemberService.createPartyMember(user, order, request.getPay());
        return new OrderResponse(order);
    }

    @GetMapping("/orders/{id}")
    @ApiOperation("Order Get")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        Order order = orderService.findOrder(orderId);
        return new OrderResponse(order);
    }

    @GetMapping("/orders/list/paging")
    @ApiOperation("Order List Paging")
    public Page<OrderResponse> getOrderList(Pageable pageable) {
        return orderService.findByPaging(pageable);
    }

}
