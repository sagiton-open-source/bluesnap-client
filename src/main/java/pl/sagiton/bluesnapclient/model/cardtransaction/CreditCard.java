package pl.sagiton.bluesnapclient.model.cardtransaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreditCard {
    private String cardNumber;
    private String encryptedCardNumber;
    private String cardLastFourDigits;
    private String cardType;
    private Integer expirationYear;
    private Integer securityCode;
    private Integer expirationMonth;
    private String encryptedSecurityCode;
    private String securityCodePfToken;
    private String cardSubType;
    private String cardCategory;
    private String issueNumber;
}
