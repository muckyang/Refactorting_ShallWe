package ShallWe.Refactoring.entity.like;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @OneToMany(mappedBy = "like")
    private List<OrderLike> orderLike = new ArrayList<>();
}

