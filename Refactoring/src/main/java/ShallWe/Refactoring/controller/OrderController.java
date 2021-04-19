package ShallWe.Refactoring.controller;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.dto.OrderRequest;
import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final TagService tagService;
    private final PartyMemberService partyMemberService;
    private final ValidationService validationService;

    @PostMapping("/orders/create")
    @ApiOperation("Order Create")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request) {
        User user = userService.findUser(request.getUserId());
        Order order = orderService.createOrder(request, user);
        tagService.createTags(order, request.getTags());
        partyMemberService.createPartyMember(user, order, request.getPay());
        return new OrderResponse(order);
    }

    @GetMapping("/orders/{id}")
    @ApiOperation("Order Get")
    public OrderResponse getOrder(@PathVariable("id") Long orderId) {
        Order order = orderService.findOrder(orderId);
        return new OrderResponse(order);
    }

    @GetMapping("/order-all")
    @ApiOperation("Order Get All")
    public List<OrderResponse> getOrderAll() {
        return orderService.getAll();
    }

    @GetMapping("/orders/list/paging")
    @ApiOperation("Order List Paging")
    public Page<OrderResponse> getOrderList(Pageable pageable) {
        return orderService.findByPaging(pageable);
    }

}
