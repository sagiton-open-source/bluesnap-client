package pl.sagiton.bluesnapclient.model.level3data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Level3Data {
    private String customerReferenceNumber;
    private Double salesTaxAmount;
    private Double freightAmount;
    private Double dutyAmount;
    private String destinationZipCode;
    private String destinationCountryCode;
    private String shipFromZipCode;
    private Double discountAmount;
    private Double taxAmount;
    private Double taxRate;
    private List<Level3DataItem> level3DataItems;
    private Boolean transactionProcessedWithL3dSupportedAcquirer;
}
