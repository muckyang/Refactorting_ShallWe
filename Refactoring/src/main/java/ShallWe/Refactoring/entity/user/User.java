package ShallWe.Refactoring.entity.user;

import ShallWe.Refactoring.entity.BaseEntity;
import ShallWe.Refactoring.entity.address.Address;
import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.user.dto.UserRequest;
import lombok.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString(of = {"name", "email", "nickname", "address", "info",})
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
    @Embedded
    private Address address;
    @Embedded
    @Column(name = "user_info")
    private Info info;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();


    public User(UserRequest request) {
        setNickname(request.getNickname());
        setName(request.getName());
        setEmail(request.getEmail());
        setPassword(request.getPassword());
        int year = request.getYear();
        int month = request.getMonth();
        int day = request.getDay();
        setInfo(new Info(year, month, day));
        String city = request.getCity();
        String street = request.getStreet();
        String detail = request.getDetail();
        setAddress(city, street, detail);
    }

    public void setAddress(String city, String street, String detail) {
        this.address = Address.builder()
                .city(city)
                .street(street)
                .detail(detail)
                .build();
    }


    public void addOrder(Order order) {
        orders.add(order);
    }

}
