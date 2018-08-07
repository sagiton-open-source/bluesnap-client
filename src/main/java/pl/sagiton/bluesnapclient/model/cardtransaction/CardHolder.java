package pl.sagiton.bluesnapclient.model.cardtransaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CardHolder {
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String state;
    private String address;
    private String address2;
    private String city;
    private String zip;
    private String phone;
    private String merchantShopperId;
    private String personalIdentificationNumber;

}
