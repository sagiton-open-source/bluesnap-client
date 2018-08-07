package pl.sagiton.bluesnapclient.model.cardtransaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Chargeback {
    private Double amount;
    private Integer chargebackTransactionId;
    private String currency;
    private Date date;

}
