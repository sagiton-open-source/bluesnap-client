package pl.sagiton.bluesnapclient.model.applepay;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BillingContactInfo {
    private String firstName;
    private String lastName; //required
    private String address1; //required
    private String address2;
    private String city;
    private String state; //Supports US and Canada states only. For states in other countries, it is necessary to include the state within the address1 or address2 property.
    private String zip;
    private String country;
    private String personalIdentificationNumber;
}
