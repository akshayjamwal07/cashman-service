package sc.com.assessment.cashman.dto.response;

import java.math.BigInteger;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCashResponse.class)
public interface CashResponse {

    String getDenominationType();

    BigInteger getDenominationValue();

    int getDenominationCount();

}
