package sc.com.assessment.cashman.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import sc.com.assessment.cashman.exception.handler.ErrorData;

@Value.Immutable
@JsonDeserialize(as = ImmutableErrorResponse.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@SuppressWarnings("immutables:incompat")
public abstract class ErrorResponse {
    public abstract List<ErrorData> getErrors();

}
