package pl.sagiton.bluesnapclient.model.vaultedshopper;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sagiton.bluesnapclient.model.applepay.BillingContactInfo;
import pl.sagiton.bluesnapclient.model.cardtransaction.CreditCard;
import pl.sagiton.bluesnapclient.model.cardtransaction.ProcessingInfo;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreditCardInfo {
    private BillingContactInfo billingContactInfo;
    private CreditCard creditCard;
    private ProcessingInfo processingInfo;

}
