package refactoring.shallWe.entity.partyMember;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import refactoring.shallWe.entity.order.Order;
import refactoring.shallWe.entity.user.User;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "party")
public class PartyMember {
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

    private enum PartyStatus{
        JOIN, CANCEL
    }

}
