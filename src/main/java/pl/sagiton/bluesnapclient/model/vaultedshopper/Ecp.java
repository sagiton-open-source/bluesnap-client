package pl.sagiton.bluesnapclient.model.vaultedshopper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Ecp {
    private String accountNumber;
    private String routingNumber;
    private String accountType;
    private String publicAccountNumber;
    private String publicRoutingNumber;
}
