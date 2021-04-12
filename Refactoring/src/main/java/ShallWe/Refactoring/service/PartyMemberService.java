package ShallWe.Refactoring.service;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.partyMember.PartyMember;
import ShallWe.Refactoring.entity.partyMember.PartyStatus;
import ShallWe.Refactoring.entity.partyMember.dto.PartyMemberRequest;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.partyMember.PartyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartyMemberService {

    @Autowired
    PartyMemberRepository partyMemberRepository;

    public void createPartyMember(User user, Order order, int price) {
        PartyMember partyMember = PartyMember.builder(user, order, price)
                .status(PartyStatus.JOIN)
                .joinDescription("글 작성자 본인 입니다")
                .build();
        order.addPartyMember(partyMember);
//        order.getMembers().add(partyMember);
//        order.addPrice(price);
        partyMemberRepository.save(partyMember);
    }

    public List<PartyMember> findByOrder(Order order) {
        return partyMemberRepository.findByOrder(order);
    }

    public PartyMember joinRequestParty(User user, Order order, PartyMemberRequest request) {
        PartyMember partyMember = PartyMember.builder(user, order, request.getPrice())
                .status(PartyStatus.WAITING)
                .joinDescription(request.getJoinDescription())
                .build();
        return partyMemberRepository.save(partyMember);
    }

    public void joinParty(Order order, User member) {
        PartyMember partyMember = findPartyMember(order, member);
        partyMember.joinApprove();
    }


    public void cancelParty(Order order, User member) {
        PartyMember partyMember = findPartyMember(order, member);
        partyMember.joinCancel();
    }

    public PartyMember findPartyMember(Order order, User member) {
        Optional<PartyMember> partyMember = partyMemberRepository.findByOrderAndMember(order, member);
        if (partyMember.isPresent()) {
            return partyMember.get();
        } else
            throw new NullPointerException("no have member");
    }
}
