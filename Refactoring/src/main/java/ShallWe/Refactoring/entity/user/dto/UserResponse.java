package ShallWe.Refactoring.entity.user.dto;


import ShallWe.Refactoring.entity.address.Address;
import ShallWe.Refactoring.entity.user.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String city;
    private String street;
    private String detail;
    private int year;
    private int month;
    private int day;

    @QueryProjection
    public UserResponse(User user) {
        setEmail(user.getEmail());
        setName(user.getName());
        setNickname(user.getNickname());
        setAddress(user.getAddress());
        setBirthDay(user.getInfo().getBirthday());
    }

    private void setAddress(Address address) {
        this.city = address.getCity();
        this.street = address.getStreet();
        this.detail = address.getDetail();
    }

    private void setBirthDay(LocalDate time) {
        this.year = time.getYear();
        this.month = time.getMonthValue();
        this.day = time.getDayOfMonth();
    }
}