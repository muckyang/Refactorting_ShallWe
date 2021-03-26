package refactoring.shallwe.entity.address;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
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