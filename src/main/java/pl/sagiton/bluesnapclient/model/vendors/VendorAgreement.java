package pl.sagiton.bluesnapclient.model.vendors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class VendorAgreement {
    private String commissionPercent;
    private String accountStatus;
    private String recurringCommission;
}
