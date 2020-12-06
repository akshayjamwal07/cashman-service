package sc.com.assessment.cashman.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableErrorData.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@SuppressWarnings("immutables:incompat")
public abstract class ErrorData {

    public abstract String getCode();

    public abstract String getTitle();

    public abstract String getDetail();
}
