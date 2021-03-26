package refactoring.shallWe.entity.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import refactoring.shallWe.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;

//    @OneToMany(mappedBy = "order")
//    private List<Comment> comments = new ArrayList<>();

    private String title;
    private String description;
    private int minPrice;

    //can self calculate
    //private int sumPrice;

    // Likes

    // Tags
    // temp
    // image , url , kakaotalk link

    //@OneToMany
    //private List<Participant> members = new ArrayList<>();

    @Column(name = "order_end_time")
    private LocalDateTime endTime;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createTime;


    public enum OrderStatus{

    }



    public enum Category {
        SHARE,DELIVERY,N_ORDER
    }
}


