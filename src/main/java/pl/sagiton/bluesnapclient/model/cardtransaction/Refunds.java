package pl.sagiton.bluesnapclient.model.cardtransaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sagiton.bluesnapclient.model.vendors.VendorRefundBalanceInfoObject;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Refunds {
    private List<Refund> refunds;
    private Double balanceAmount;
    private Double vendorBalanceAmount;
    private VendorRefundBalanceInfoObject vendorsBalanceInfo;
}
