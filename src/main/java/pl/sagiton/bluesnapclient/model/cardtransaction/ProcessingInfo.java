package pl.sagiton.bluesnapclient.model.cardtransaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProcessingInfo {
    private String processingStatus;
    private String cvvResponseCode;
    private String avsResponseCodeZip;
    private String avsResponseCodeAddress;
    private String avsResponseCodeName;
}
