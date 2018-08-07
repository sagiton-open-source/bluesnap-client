package pl.sagiton.bluesnapclient.model.vaultedshopper;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sagiton.bluesnapclient.model.applepay.WalletResponse;
import pl.sagiton.bluesnapclient.model.cardtransaction.CreditCard;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LastPaymentInfo {
    private String paymentMethod;
    private CreditCard creditCard;
    private Ecp ecp;
    private WalletResponse wallet;

}
