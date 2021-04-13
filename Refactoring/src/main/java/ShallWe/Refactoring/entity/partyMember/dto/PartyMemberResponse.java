package ShallWe.Refactoring.entity.partyMember.dto;

import ShallWe.Refactoring.entity.partyMember.PartyMember;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class PartyMemberResponse {
    private String orderTitle;
    private String userNickname;
    private int price;
    private String joinDescription;
    private String status;

    @QueryProjection
    public PartyMemberResponse(PartyMember partyMember){
        orderTitle = partyMember.getOrder().getTitle();
        userNickname = partyMember.getUser().getNickname();
        price = partyMember.getPrice();
        joinDescription = partyMember.getJoinDescription();
        status = partyMember.getStatus().toString();
    }
}
