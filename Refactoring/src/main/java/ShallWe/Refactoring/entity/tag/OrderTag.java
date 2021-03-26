package ShallWe.Refactoring.entity.tag;

import lombok.Getter;
import lombok.Setter;
import ShallWe.Refactoring.entity.order.Order;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name ="order_tag")
public class OrderTag {

    //TODO 양방향 fk로 id 제거 예정
    @Id
    @GeneratedValue
    @Column(name ="order_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
