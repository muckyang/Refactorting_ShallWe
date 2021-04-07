package ShallWe.Refactoring.controller;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.dto.OrderRequest;
import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.entity.user.dto.UserResponse;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.tag.TagRepository;
import ShallWe.Refactoring.service.order.OrderService;
import ShallWe.Refactoring.service.partyMember.PartyMemberService;
import ShallWe.Refactoring.service.tag.TagService;
import ShallWe.Refactoring.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @Autowired
    PartyMemberService partyMemberService;

    @GetMapping("/list")
    @ApiOperation("Order List Paging")
    public Page<OrderResponse> getOrderList(Pageable pageable) {
        return orderRepository.getUserPaging(pageable);
    }


    @PostMapping("/create")
    @ApiOperation("Order Create")
    public @ResponseBody OrderResponse createOrder(@RequestBody OrderRequest request) {

        User user = userService.findUser(request.getUserId());

        Order order = orderService.createOrder(request, user);
        tagService.createTags(order,request.getTags());
        partyMemberService.createPartyMember(user,order,request.getPay());
        return new OrderResponse(order);
    }
}
