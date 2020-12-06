package sc.com.assessment.cashman.model;

import java.math.BigInteger;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableDenomination.class)
@JsonSerialize(as = ImmutableDenomination.class)
public interface Denomination {

    String getDenominationType();

    BigInteger getDenominationValue();

    int getDenominationCount();
}
