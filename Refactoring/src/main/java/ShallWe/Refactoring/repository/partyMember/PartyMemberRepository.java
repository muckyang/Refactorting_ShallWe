package ShallWe.Refactoring.repository.partyMember;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.partyMember.PartyMember;
import ShallWe.Refactoring.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartyMemberRepository extends JpaRepository<PartyMember,Long> {
    Optional<PartyMember> findByOrderAndMember(Order order, User member);
    List<PartyMember> findByOrder(Order order);
}
