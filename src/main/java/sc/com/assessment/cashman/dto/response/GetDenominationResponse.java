package sc.com.assessment.cashman.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableGetDenominationResponse.class)
public interface GetDenominationResponse {

   List<DenominationResponse> getDenominationResponse();
}
