package pl.sagiton.bluesnapclient.model.vendors;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Vendor{
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String state;
    private String zip;
    private String taxId;
    private String vatId;
    private String defaultPayoutCurrency;
    private String frequency;
    private Integer delay;
    private VendorPrincipal vendorPrincipal;
    private List<PayoutInfo> payoutInfo;
    private VendorAgreement vendorAgreement;
    private Integer vendorId;
    private Verification verification;
}
