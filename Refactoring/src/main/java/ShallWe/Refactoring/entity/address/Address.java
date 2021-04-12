package ShallWe.Refactoring.entity.address;

import ShallWe.Refactoring.entity.order.dto.OrderRequestBuilder;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "addressBuilder")
@ToString(of = {"city", "street", "detail"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {
    private String city;
    private String street;
    private String detail;

    public static AddressBuilder builder(String city, String street, String detail) {
        if(city == null || street == null || detail == null){
            throw new IllegalArgumentException("null argument exception");
        }
        return addressBuilder().city(city).street(street).detail(detail);
    }


}





