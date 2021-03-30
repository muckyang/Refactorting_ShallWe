package ShallWe.Refactoring.entity.user;

import ShallWe.Refactoring.dto.user.UserRequest;
import ShallWe.Refactoring.entity.BaseEntity;
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
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {
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

    public User(UserRequest request) {
        this.nickname = request.getNickname();
        this.name = request.getName();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.adapterAddress(request);
        this.info = new Info(request.getYear(), request.getMonth(), request.getDay());
    }

    public void adapterAddress(UserRequest request) {
        String city = request.getCity();
        String street = request.getStreet();
        String detail = request.getDetail();
        setAddress(city, street, detail);
    }

    public void setAddress(String city,String street,String detail) {
        this.address = new Address(city, street, detail);
    }

}
