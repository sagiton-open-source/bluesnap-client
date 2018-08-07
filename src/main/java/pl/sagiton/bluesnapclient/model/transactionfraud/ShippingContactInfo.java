package pl.sagiton.bluesnapclient.model.transactionfraud;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ShippingContactInfo {
    private String firstName;
    private String lastName;
    private String address;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
}
