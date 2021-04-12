package ShallWe.Refactoring.entity.partyMember;

import ShallWe.Refactoring.entity.BaseEntity;
import ShallWe.Refactoring.entity.order.OrderStatus;
import lombok.*;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.user.User;

import javax.persistence.*;

@Entity
@Getter
@Builder(builderMethodName = "partyMemberBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString(of={"id","order","member","price","status"})
@Table(name = "party")
public class PartyMember  extends BaseEntity {
    //TODO id 필드 삭제 예정) Order, User 합쳐서 키로 활용
    @Id
    @GeneratedValue
    @Column(name = "party_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User member;
    private int price;

    @Enumerated(EnumType.STRING)
    private PartyStatus status;
    private String joinDescription;

    public static PartyMemberBuilder builder(User user ,Order order ,int price){
        if(user == null || order==null){
            throw new IllegalArgumentException("null pointer exception");
        }
        else if(price < 1000){
            throw new IllegalArgumentException("최소 금액 미충족 ");
        }
        return partyMemberBuilder()
                .member(user)
                .order(order)
                .price(price);
    }

    public void setStatus(PartyStatus status){
        this.status = status;
    }

    public void joinApprove(){
        if(this.status == PartyStatus.WAITING){
            this.setStatus(PartyStatus.JOIN);
            order.addPrice(this.price);
        }else
            throw new IllegalStateException("is not Waiting!");
    }

    public void joinCancel(){
        if(this.status == PartyStatus.WAITING){
            this.setStatus(PartyStatus.CANCEL);
        }else
            throw new IllegalStateException("is not Waiting!");
    }

}
