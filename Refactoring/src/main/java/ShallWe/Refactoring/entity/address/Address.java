package ShallWe.Refactoring.entity.address;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@ToString(of={"city","street","detail"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {
    private String city;
    private String street;
    private String detail;

    public Address(String city, String street, String detail) {
        this.city = city;
        this.street = street;
        this.detail = detail;
    }
}





