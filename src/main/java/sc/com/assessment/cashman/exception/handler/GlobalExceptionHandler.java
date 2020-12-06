package sc.com.assessment.cashman.exception.handler;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Collections;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sc.com.assessment.cashman.dto.response.ErrorResponse;
import sc.com.assessment.cashman.dto.response.ImmutableErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {

    private static final Logger LOG = getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> defaultErrorHandler(final Exception ex) {
        LOG.error("Exception propagated to Controller Advice", ex);

        final ErrorResponse errorResponse = ImmutableErrorResponse.builder().errors(Collections.singleton(
                ImmutableErrorData.builder()
                        .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                        .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .detail(ex.getMessage())
                        .build()
                )
        ).build();

        return standardErrorResponse(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
