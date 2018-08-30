package pl.sagiton.bluesnapclient.model.transactionmetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TransactionMetaData {
    private List<MetaData> metaData;
}
