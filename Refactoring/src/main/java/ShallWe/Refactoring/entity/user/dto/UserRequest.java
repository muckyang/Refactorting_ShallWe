package ShallWe.Refactoring.entity.user.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRequest {
    private Long id;
    @Email(message = "Email 형식이 아닙니다.")
    @NotNull
    private String email;
    @NotNull(message = "값이 필요합니다.")
    private String password;
    @NotNull
    private String name;
    @NotNull
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
