package refactoring.shallWe.entity.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
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