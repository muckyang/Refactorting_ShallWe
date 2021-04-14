package ShallWe.Refactoring.controller;

import ShallWe.Refactoring.entity.comment.Comment;
import ShallWe.Refactoring.entity.comment.dto.CommentRequest;
import ShallWe.Refactoring.entity.comment.dto.CommentResponse;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.service.CommentService;
import ShallWe.Refactoring.service.OrderService;
import ShallWe.Refactoring.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/comment")
    @ApiOperation("댓글 작성")
    public CommentResponse addComment(@RequestBody CommentRequest request) {
        User user = userService.findUser(request.getUserId());
        Order order = orderService.findOrder(request.getOrderId());
        return commentService.createComment(order,user ,request.getContent());
    }

    @PatchMapping("/comment/{commentId}")
    @ApiOperation("댓글 수정")
    public CommentResponse updateComment(@PathVariable("commentId") Long commentId,
                                         @RequestBody CommentRequest request){
        String updateText = request.getContent();
        commentService.patchText(commentId,updateText);

        return commentService.findById(commentId);
    }

}
