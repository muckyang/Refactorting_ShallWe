package ShallWe.Refactoring.entity.partyMember;

import ShallWe.Refactoring.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.user.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    //TODO 1000원 이상만 가능 하도록
    private int price;

    @Enumerated(EnumType.STRING)
    private PartyStatus status;
    private String joinDescription;

    public void setOrder(Order order) {
        this.order = order;
        order.getMembers().add(this);
        order.setSumPrice(order.getSumPrice() + this.price);
    }

}
