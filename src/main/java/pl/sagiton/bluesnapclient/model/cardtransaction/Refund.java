package pl.sagiton.bluesnapclient.model.cardtransaction;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sagiton.bluesnapclient.model.vendors.VendorRefundInfo;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Refund {
    private Double amount;
    private Integer refundTransactionId;
    private String currency;
    private Date date;
    private Double vendorAmount;
    private VendorRefundInfo vendorRefundInfo;
}
