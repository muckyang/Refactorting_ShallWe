package ShallWe.Refactoring.repository.partyMember;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.partyMember.PartyStatus;
import ShallWe.Refactoring.entity.partyMember.dto.PartyMemberResponse;

import java.util.List;

public interface PartyMemberCustom {
    List<PartyMemberResponse> findByOrderAndStatus(Order order, PartyStatus status);
    List<PartyMemberResponse> findByOrder(Order order) ;
}
