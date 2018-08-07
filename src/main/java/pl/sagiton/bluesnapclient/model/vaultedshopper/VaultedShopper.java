package pl.sagiton.bluesnapclient.model.vaultedshopper;

import lombok.*;
import pl.sagiton.bluesnapclient.model.applepay.Wallet;
import pl.sagiton.bluesnapclient.model.cardtransaction.CreditCard;
import pl.sagiton.bluesnapclient.model.cardtransaction.FraudResultInfo;
import pl.sagiton.bluesnapclient.model.transactionfraud.ShippingContactInfo;
import pl.sagiton.bluesnapclient.model.transactionfraud.TransactionFraudInfo;


@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class VaultedShopper {
    private String firstName;
    private String lastName;
    private String merchantShopperId;
    private String email;
    private String phone;
    private String address;
    private String address2;
    private String city;
    private String country;
    private String state;
    private String zip;
    private String companyName;
    private String shopperCurrency;
    private PaymentSources paymentSources;
    private ShippingContactInfo shippingContactInfo;
    private String personalIdentificationNumber;
    private String softDescriptor;
    private String descriptorPhoneNumber;
    private Wallet wallet;
    private TransactionFraudInfo transactionFraudInfo;
    private Integer vaultedShopperId;
    private LastPaymentInfo lastPaymentInfo;
    private FraudResultInfo fraudResultInfo;
    private CreditCard creditCard;

}
