package pl.sagiton.bluesnapclient.model.vaultedshopper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SepaDirectDebit {
    private String iban;
    private String bic;
    private String ibanFirstFour;
    private String ibanLastFour;
    private String mandateId;
    private String mandateDate;
    private String preNotificationText;
    private String preNotificationTranslationRef;
}
