package pl.sagiton.bluesnapclient.model.vendors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class VendorPrincipal {
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String address2;
    private String city;
    private String country;
    private String state;
    private String zip;
    private String dob;
    private String personalIdentificationNumber;
    private String driverLicenseNumber;
    private String passportNumber;
}
