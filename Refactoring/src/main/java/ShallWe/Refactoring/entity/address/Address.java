package ShallWe.Refactoring.entity.address;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@ToString(of = {"city", "street", "detail"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Address {
    private  String city;
    private  String street;
    private  String detail;

}





