package ShallWe.Refactoring.entity.user.dto;


import lombok.Data;

@Data
public class UserRequest {
    private Long id;
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
