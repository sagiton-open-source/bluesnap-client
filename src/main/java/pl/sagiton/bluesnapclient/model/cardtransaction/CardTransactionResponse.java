package pl.sagiton.bluesnapclient.model.cardtransaction;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sagiton.bluesnapclient.model.applepay.Wallet;
import pl.sagiton.bluesnapclient.model.level3data.Level3Data;
import pl.sagiton.bluesnapclient.model.threeDSecure.ThreeDSecure;
import pl.sagiton.bluesnapclient.model.transactionmetadata.TransactionMetaData;
import pl.sagiton.bluesnapclient.model.vendors.VendorInfo;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CardTransactionResponse {

    private Wallet wallet;
    private BigDecimal amount;
    private Integer vaultedShopperId;
    private String merchantTransactionId;
    private String softDescriptor;
    private String descriptorPhoneNumber;
    private List<VendorInfo> vendorsInfo;
    private CardHolder cardHolderInfo;
    private String currency;
    private FraudResultInfo fraudResultInfo;
    private CreditCard creditCard;
    private String cardTransactionType;
    private ThreeDSecure threeDSecure;
    private TransactionMetaData transactionMetaData;
    private Level3Data level3Data;
    private Boolean storeCard;
    private ProcessingInfo processingInfo;
    private Double openToCapture;
    private String transactionId;
    private List<Chargeback> chargebacks;
    private List<Refunds> refunds;

}
