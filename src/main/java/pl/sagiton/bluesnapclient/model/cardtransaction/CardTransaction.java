package pl.sagiton.bluesnapclient.model.cardtransaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sagiton.bluesnapclient.model.applepay.Wallet;
import pl.sagiton.bluesnapclient.model.level3data.Level3Data;
import pl.sagiton.bluesnapclient.model.threeDSecure.ThreeDSecure;
import pl.sagiton.bluesnapclient.model.transactionfraud.TransactionFraudInfo;
import pl.sagiton.bluesnapclient.model.transactionmetadata.TransactionMetaData;
import pl.sagiton.bluesnapclient.model.vendors.VendorInfo;
import pl.sagiton.bluesnapclient.model.vendors.VendorsInfo;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CardTransaction {
    private Long walletId;  //visa or masterpass required
    private Double amount;
    private Integer vaultedShopperId;
    private String merchantTransactionId;
    private String softDescriptor;
    private String descriptorPhoneNumber;
    private VendorsInfo vendorsInfo; //marketplace required
    private VendorInfo vendorInfo; //marketplace required
    private CardHolder cardHolderInfo; //required if card data are sent or pfToken and shopper is new
    private String currency;
    private TransactionFraudInfo transactionFraudInfo;
    private CreditCard creditCard;
    private String cardTransactionType; // required, value must be AUTH_CAPTURE
    private ThreeDSecure threeDSecure;
    private TransactionMetaData transactionMetaData;
    private String pfToken; // required if using Hosted Payment Fields
    private String transactionId; // required if using Hosted Payment Fields
    private Wallet wallet; //apple pay required
    private Level3Data level3Data;
    private Boolean storeCard;
    private ProcessingInfo processingInfo;
    private Double openToCapture;
    private List<Chargeback> chargebacks;
    private List<Refunds> refunds;
}
