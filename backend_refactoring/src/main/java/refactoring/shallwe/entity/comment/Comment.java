package refactoring.shallwe.entity.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import refactoring.shallwe.entity.order.Order;
import refactoring.shallwe.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

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

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createTime;

    //TODO update -> Logger / status change Updated
    public enum CommentStatus{
        NORMAL, UPDATED , BAN
    }

    public void setOrder(Order order){
        this.order = order;
        order.addComment(this);
    }



}
