package ShallWe.Refactoring.controller;

import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.user.dto.UserResponse;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.tag.TagRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/list")
    @ApiOperation("Order List Paging")
    public Page<OrderResponse> getOrderList(Pageable pageable){
        return orderRepository.getUserPaging(pageable);
    }


}
