package sc.com.assessment.cashman.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableDenominationResponse.class)
public interface DenominationResponse {

    String getDenominationType();

    int getDenominationCount();
}
