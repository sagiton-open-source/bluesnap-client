package pl.sagiton.bluesnapclient.model.vendors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Verification {
    private String payoutStatus;
    private String processingStatus;
    private String declineReason;
    private List<Object> missingItems;
}
