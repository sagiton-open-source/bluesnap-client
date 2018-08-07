package pl.sagiton.bluesnapclient.model.applepay;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class WalletResponse extends Wallet {
    private BillingContactInfo billingContactInfo;

}
