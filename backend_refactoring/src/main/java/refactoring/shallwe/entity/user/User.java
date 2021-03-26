package refactoring.shallwe.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import refactoring.shallwe.entity.address.Address;
import refactoring.shallwe.entity.order.Order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String nickname;
    private Address address;
    @Column(name = "user_info")
    private Info info;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();


    @Column(insertable = false, updatable = false)
    private LocalDateTime createTime;

}
