package ShallWe.Refactoring.entity.partyMember.dto;

import ShallWe.Refactoring.entity.partyMember.PartyStatus;
import lombok.Data;

@Data
public class PartyMemberRequest {
    private Long id;
    private Long orderId;
    private Long memberId;
    private int price;
    private String joinDescription;
}
