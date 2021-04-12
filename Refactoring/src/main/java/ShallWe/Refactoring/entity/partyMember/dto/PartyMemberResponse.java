package ShallWe.Refactoring.entity.partyMember.dto;

import lombok.Data;

@Data
public class PartyMemberResponse {
    private String memberName;
    private int price;
    private String joinDescription;
}
