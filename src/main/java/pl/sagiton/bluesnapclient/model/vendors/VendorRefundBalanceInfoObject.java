package pl.sagiton.bluesnapclient.model.vendors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class VendorRefundBalanceInfoObject {
    private Double vendorAmount;
    private Integer vendorId;
}
