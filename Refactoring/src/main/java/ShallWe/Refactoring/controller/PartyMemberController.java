package ShallWe.Refactoring.controller;

import ShallWe.Refactoring.RunApplication;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.partyMember.PartyMember;
import ShallWe.Refactoring.entity.partyMember.PartyStatus;
import ShallWe.Refactoring.entity.partyMember.dto.PartyMemberRequest;
import ShallWe.Refactoring.entity.partyMember.dto.PartyMemberResponse;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.service.OrderService;
import ShallWe.Refactoring.service.PartyMemberService;
import ShallWe.Refactoring.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PartyMemberController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PartyMemberService partyMemberService;
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/partyMembers")
    @ApiOperation("Add Member")
    public String addPartyMember(@RequestBody PartyMemberRequest request) {
        User user = userService.findUser(request.getMemberId());
        Order order = orderService.findOrder(request.getOrderId());
        PartyMember partyMember = partyMemberService.joinRequestParty(user, order, request);
        order.getMembers().add(partyMember);
        return "PartyMember Add request Success";
    }

    @GetMapping("/join-members/{orderId}")
    @ApiOperation("Get Join List")
    public List<PartyMemberResponse> getJoinedMembers(@PathVariable("orderId") Long orderId) {
        Order order = orderService.findOrder(orderId);
        List<PartyMemberResponse> partyMembers = partyMemberService.findByOrderAndStatus(order, PartyStatus.JOIN);
        logger.info(partyMembers.size() + "");

        return partyMembers;
    }

    @GetMapping("/members/{orderId}")
    @ApiOperation("Get All List")
    public List<PartyMemberResponse> getPartyMembers(@PathVariable("orderId") Long orderId) {
        Order order = orderService.findOrder(orderId);
        List<PartyMemberResponse> partyMembers = partyMemberService.findByOrder(order);
        logger.info(partyMembers.size() + "");

        return partyMembers;
    }

    //TODO 게시물 주인 / 게시물 번호 / 변경될 인원
    @PatchMapping("/partyMembers/join/{userId}/{orderId}/{memberId}")
    @ApiOperation("Do Join Member")
    public String joinMember(@PathVariable("userId") Long userId,
                             @PathVariable("orderId") Long orderId,
                             @PathVariable("memberId") Long memberId) {
        //로그인된 회원이 해당게시물의 주인인지 확인
        User user = userService.findUser(userId);
        Order order = orderService.findOrder(orderId);
        User member = userService.findUser(memberId);
        if (order.isWriter(user)) {
            partyMemberService.joinParty(order, member);
            return "join success";
        }
        return "your is not writer";
    }

    @PatchMapping("/partyMembers/cancel/{userId}/{orderId}/{memberId}")
    @ApiOperation("Do Cancel Member")
    public String cancelMember(@PathVariable("userId") Long userId,
                               @PathVariable("orderId") Long orderId,
                               @PathVariable("memberId") Long memberId) {
        User user = userService.findUser(userId);
        Order order = orderService.findOrder(orderId);
        User member = userService.findUser(memberId);
        if (order.isWriter(user)) {
            partyMemberService.cancelParty(order, member);
            return "cancel success";
        }
        return "your is not writer";
    }
}
