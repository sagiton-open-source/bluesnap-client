package pl.sagiton.bluesnapclient.model.applepay;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ApplePay {
    private String encodedPaymentToken;
    private String cardLastFourDigits;
    private String cardType;
    private String cardSubType; //Credit card sub-type, such as Credit or Debit.
    private String dpanLastFourDigits; //Last four digits of device account number.
    private String dpanExpirationMonth; //Expiration month of the device account number.
    private String dpanExpirationYear; //Expiration year of the device account number.

}
