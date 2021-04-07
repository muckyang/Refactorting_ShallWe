package ShallWe.Refactoring.entity.order;

import ShallWe.Refactoring.entity.BaseEntity;
import ShallWe.Refactoring.entity.order.dto.OrderRequest;
import ShallWe.Refactoring.entity.tag.Tag;
import lombok.*;
import ShallWe.Refactoring.entity.comment.Comment;
import ShallWe.Refactoring.entity.like.OrderLike;
import ShallWe.Refactoring.entity.partyMember.PartyMember;
import ShallWe.Refactoring.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"user", "title", "description"})
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<Comment> comments = new ArrayList<>();

    private String title;
    private String description;
    private int goalPrice;
    private int sumPrice;

    private int likeCount;
    private int commentCount;

    public Order(OrderRequest request,User user){
        this.setUser(user);
        this.setTitle(request.getTitle());
        this.setDescription(request.getDescription());
        this.setGoalPrice(request.getGoalPrice());
        this.setStatus(OrderStatus.WAITING);
    }


    @OneToMany(mappedBy = "order")
    private List<OrderLike> orderLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<Tag> tags = new ArrayList<>();

    // TODO temp //저장, 임시저장 , 활성화  삭제해도 무방할듯.
    // image , url , kakaotalk link

    @OneToMany(mappedBy = "member")
    private List<PartyMember> members = new ArrayList<>();

    @Column(name = "order_end_time")
    private LocalDateTime endTime;

    // 연관 관계 편의 메소드
    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void addPrice(int price) {
        this.sumPrice += price;
        if (this.overGoalPrice()) {
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

    public boolean overGoalPrice() {
        return this.goalPrice <= this.sumPrice;
    }

}


