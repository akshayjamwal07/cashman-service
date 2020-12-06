package sc.com.assessment.cashman.dto.request;

import java.math.BigInteger;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCash.class)
public interface Cash {
    String getDenominationType();

    BigInteger getDenominationValue();

    int getDenominationCount();
}
