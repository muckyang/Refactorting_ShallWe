package ShallWe.Refactoring.entity.user;

import ShallWe.Refactoring.entity.address.Address;
import ShallWe.Refactoring.entity.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

    @GeneratedValue
    @Column(insertable = false, updatable = false)
    private LocalDateTime createTime;

    public void setAddress(Address address){
        this.address =address;

    }

}
