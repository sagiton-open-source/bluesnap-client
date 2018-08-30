package pl.sagiton.bluesnapclient.model.vendors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(exclude = "intermediaryBankInfo")
@ToString
public class PayoutInfo {
    private String payoutType;
    private String baseCurrency;
    private String nameOnAccount;
    private String bankAccountClass;
    private String bankAccountType;
    private String bankName;
    private Integer bankId;
    private String city;
    private String address;
    private String country;
    private String state;
    private String zip;
    private Integer bankAccountId;
    private String iban;
    private String swiftBic;
    private Integer minimalPayoutAmount;
    private String paymentReference;
    private Object intermediaryBankInfo;
}
