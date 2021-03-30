package ShallWe.Refactoring.dto.user;

import ShallWe.Refactoring.entity.address.Address;
import ShallWe.Refactoring.entity.user.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserResponse {
    private String email;
    private String name;
    private String nickname;
    private String city;
    private String street;
    private String detail;
    private int year;
    private int month;
    private int day;

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.setAddress(user.getAddress());
        this.setBirthDay(user.getInfo().getBirthday());
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
