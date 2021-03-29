package ShallWe.Refactoring.entity.comment;

import ShallWe.Refactoring.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String content;


    //TODO update -> Logger / status change Updated
    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    public void setOrder(Order order){
        this.order = order;
        order.addComment(this);
    }



}
