package ShallWe.Refactoring.repository.partyMember;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.partyMember.PartyStatus;
import ShallWe.Refactoring.entity.partyMember.dto.PartyMemberResponse;
import ShallWe.Refactoring.entity.partyMember.dto.QPartyMemberResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static ShallWe.Refactoring.entity.partyMember.QPartyMember.*;

public class PartyMemberCustomImpl implements PartyMemberCustom{

    JPAQueryFactory queryFactory;

    public PartyMemberCustomImpl(EntityManager em ){
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PartyMemberResponse> findByOrderAndStatus(Order order, PartyStatus status) {
        return queryFactory.select(new QPartyMemberResponse(partyMember))
                .from(partyMember)
                .where(
                        partyMember.order.eq(order)
                        .and(partyMember.status.eq(status))
                )
                .fetch();
    }

    @Override
    public List<PartyMemberResponse> findByOrder(Order order) {
        return queryFactory.select(new QPartyMemberResponse(partyMember))
                .from(partyMember)
                .where(partyMember.order.eq(order))
                .fetch();
    }
}
