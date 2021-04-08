package ShallWe.Refactoring.entity.tag;

import ShallWe.Refactoring.entity.order.Order;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"name"})
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tag_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    public Tag(String name){
        this.name = name;
    }
    public void setOrder(Order order) {
        order.addTag(this);
        this.order = order;
    }

}
