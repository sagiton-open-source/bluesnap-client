package pl.sagiton.bluesnapclient.model.threeDSecure;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ThreeDSecure{
    private String threeDSecureResultToken;
    private String eci;
    private String cavv;
    private  String xid;
    private String authenticationResult;

}