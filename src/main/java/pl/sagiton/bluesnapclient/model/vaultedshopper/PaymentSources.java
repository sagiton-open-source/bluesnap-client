package pl.sagiton.bluesnapclient.model.vaultedshopper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PaymentSources {
    private List<CreditCardInfo> creditCardInfo;
    private EcpInfo ecpInfo;
    private List<Object> sepaDirectDebitInfo;

}
