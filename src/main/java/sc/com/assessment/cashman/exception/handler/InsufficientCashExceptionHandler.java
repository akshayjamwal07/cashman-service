package sc.com.assessment.cashman.exception.handler;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Collections;

import org.slf4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sc.com.assessment.cashman.dto.response.ErrorResponse;
import sc.com.assessment.cashman.dto.response.ImmutableErrorResponse;
import sc.com.assessment.cashman.exception.InsufficientCashException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class InsufficientCashExceptionHandler extends BaseExceptionHandler {

    private static final Logger LOG = getLogger(InsufficientCashExceptionHandler.class);

    @ExceptionHandler(InsufficientCashException.class)
    public ResponseEntity<ErrorResponse> defaultErrorHandler(final InsufficientCashException ex) {
        LOG.warn("InsufficientCashException propagated to Controller Advice {}", ex.getMessage());

        final ErrorResponse errorResponse = ImmutableErrorResponse.builder().errors(Collections.singleton(
                ImmutableErrorData.builder()
                        .code(ex.getErrorCode())
                        .title(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .detail(ex.getMessage())
                        .build()
                )
        ).build();

        return standardErrorResponse(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
