package pl.sagiton.bluesnapclient.model.applepay;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Wallet {
    private ApplePay applePay;
    private BillingContactInfo billingContactInfo;
}
