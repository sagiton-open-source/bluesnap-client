package pl.sagiton.bluesnapclient.model.vaultedshopper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sagiton.bluesnapclient.model.applepay.BillingContactInfo;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SepaDirectDebitInfo {
    private BillingContactInfo billingContactInfo;
    private SepaDirectDebit sepaDirectDebit;

}
