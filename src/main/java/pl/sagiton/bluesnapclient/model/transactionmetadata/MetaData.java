package pl.sagiton.bluesnapclient.model.transactionmetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MetaData {
    private String metaKey;
    private String metaValue;
    private String metaDescription;
}
