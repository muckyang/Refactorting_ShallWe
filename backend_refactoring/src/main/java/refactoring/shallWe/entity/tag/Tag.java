package refactoring.shallWe.entity.tag;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue
    @Column(name ="tag_id")
    private Long id;

    @Column(name ="tag_name",unique = true)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<OrderTag> orderTags = new ArrayList<>();

}
