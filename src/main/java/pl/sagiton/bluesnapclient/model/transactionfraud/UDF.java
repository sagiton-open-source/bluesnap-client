package pl.sagiton.bluesnapclient.model.transactionfraud;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UDF {
    private String udfName; //required
    private String udfValue; //required
}
