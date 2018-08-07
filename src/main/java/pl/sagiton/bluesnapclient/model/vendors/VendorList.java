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
public class VendorList{
    private Integer totalResults;
    private Boolean lastPage;
    private List<Vendor> vendor;
}
