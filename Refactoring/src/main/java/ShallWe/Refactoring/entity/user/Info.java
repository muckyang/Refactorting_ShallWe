package ShallWe.Refactoring.entity.user;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor
public class Info {
    private int point;
    private int grade;
    private LocalDate birthday;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public Info(int year ,int month, int day){
        this.birthday = LocalDate.of(year,month,day);
        this.setPoint(1000);
        this.userStatus = UserStatus.ACTIVE;
    }

    public void setPoint(int point) {
        this.point = point;
        this.grade = (point / 1000) + 1;
    }

}
