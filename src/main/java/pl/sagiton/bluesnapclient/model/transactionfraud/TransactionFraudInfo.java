package pl.sagiton.bluesnapclient.model.transactionfraud;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TransactionFraudInfo {
private String fraudSessionId;
    private String shopperIpAddress; //Shopper's IP address. Should be a valid IPv4 or IPv6 address.
    private String company;
    private ShippingContactInfo shippingContactInfo;
    private String enterpriseSiteId;
    private List<UDF> enterpriseUdfs;
}
