package ShallWe.Refactoring.repository.partyMember;

import ShallWe.Refactoring.entity.partyMember.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyMemberRepository extends JpaRepository<PartyMember,Long> {
}
