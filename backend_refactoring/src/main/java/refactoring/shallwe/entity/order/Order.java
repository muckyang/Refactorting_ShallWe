package refactoring.shallwe.entity.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import refactoring.shallwe.entity.comment.Comment;
import refactoring.shallwe.entity.like.OrderLike;
import refactoring.shallwe.entity.partyMember.PartyMember;
import refactoring.shallwe.entity.tag.OrderTag;
import refactoring.shallwe.entity.user.User;

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
    private int goalPrice;

    private int sumPrice;

    private int likeCount;
    private int commentCount;

    @OneToMany(mappedBy = "order")
    private List<OrderLike> orderLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<OrderTag> orderTags = new ArrayList<>();

    // TODO temp 뭔지 확인 필요
    // image , url , kakaotalk link

    @OneToMany(mappedBy = "member")
    private List<PartyMember> members = new ArrayList<>();

    @Column(name = "order_end_time")
    private LocalDateTime endTime;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createTime;


    // 연관 관계 편의 메소드
    public void setUser(User user){
        this.user= user;
        user.getOrders().add(this);
    }

    public void addPrice(int price){
        this.sumPrice += price;
        if(this.overGoalPrice()){
            this.status = OrderStatus.COMP_READY;
        }
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.commentCount++;
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        this.commentCount--;
    }

    public boolean overGoalPrice(){
        return this.goalPrice <= this.sumPrice;
    }



    public enum OrderStatus{
        WAITING, ON_GOING ,COMP_READY ,FAIL , SUCCEEDED
    }

    public enum Category {
        SHARE, DELIVERY, N_ORDER
    }
}


