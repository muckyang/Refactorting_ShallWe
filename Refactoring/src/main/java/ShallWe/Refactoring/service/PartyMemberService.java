package ShallWe.Refactoring.service;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.partyMember.PartyMember;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.partyMember.PartyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyMemberService {

    @Autowired
    PartyMemberRepository partyMemberRepository;

    public void createPartyMember(User user , Order order , int price){
        PartyMember partyMember = new PartyMember(user,order,price);
        order.getMembers().add(partyMember);
        partyMemberRepository.save(partyMember);
    }
}
