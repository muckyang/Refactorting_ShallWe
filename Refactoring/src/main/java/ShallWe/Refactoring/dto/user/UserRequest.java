package ShallWe.Refactoring.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequest {
    private String email;
    private String password;
    private String name;
    private String nickname;

    //address
    private String city;
    private String street;
    private String detail;

    //info_birthday
    private int year;
    private int month;
    private int day;

}
