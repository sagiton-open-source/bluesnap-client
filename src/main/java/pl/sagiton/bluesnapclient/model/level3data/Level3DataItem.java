package pl.sagiton.bluesnapclient.model.level3data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Level3DataItem {
    private Double lineItemTotal;
    private String commodityCode;
    private String description;
    private Double discountAmount;
    private String discountIndicator;
    private String grossNetIndicator;
    private String productCode;
    private Double itemQuantity;
    private Double taxAmount;
    private Double taxRate;
    private String taxType;
    private Double unitCost;
    private String unitOfMeasure;

}
