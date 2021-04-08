package ShallWe.Refactoring.entity.user;

import ShallWe.Refactoring.entity.BaseEntity;
import ShallWe.Refactoring.entity.address.Address;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.user.dto.UserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of = {"name", "email", "nickname","address","info",})
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        int year = request.getYear();
        int month = request.getMonth();
        int day = request.getDay();

        this.setInfo(new Info(year, month, day));
        this.adapting(request);
    }

    public void adapterAddress(UserRequest request) {
        String city = request.getCity();
        String street = request.getStreet();
        String detail = request.getDetail();
        setAddress(city, street, detail);
    }

    public void setAddress(String city, String street, String detail) {
        this.address = new Address(city, street, detail);
    }

    public void adapting(UserRequest request) {
        this.setNickname(request.getNickname());
        this.setName(request.getName());
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.adapterAddress(request);
    }

}
