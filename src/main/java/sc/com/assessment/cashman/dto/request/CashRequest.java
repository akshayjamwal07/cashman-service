package sc.com.assessment.cashman.dto.request;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCashRequest.class)
public interface CashRequest {

    List<Cash> getCash();
}
