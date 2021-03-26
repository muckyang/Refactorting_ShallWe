package refactoring.shallWe.entity.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import refactoring.shallWe.entity.comment.Comment;
import refactoring.shallWe.entity.partyMember.PartyMember;
import refactoring.shallWe.entity.tag.OrderTag;
import refactoring.shallWe.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<Comment> comments = new ArrayList<>();

    private String title;
    private String description;
    private int minPrice;
    @Column(insertable = false)
    private int sumPrice;
    //can self calculate
    //private int sumPrice;

    // Likes

    @OneToMany(mappedBy = "order")
    private List<OrderTag> orderTags = new ArrayList<>();

    // temp
    // image , url , kakaotalk link

    @OneToMany(mappedBy = "member")
    private List<PartyMember> members = new ArrayList<>();

    @Column(name = "order_end_time")
    private LocalDateTime endTime;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createTime;


    public enum OrderStatus{
       WAITING, ONGOING ,COMP_READY ,FAILED, SUCCESSED
    }

    public enum Category {
        SHARE, DELIVERY, N_ORDER
    }
}


