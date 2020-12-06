package sc.com.assessment.cashman.exception.handler;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sc.com.assessment.cashman.dto.response.ErrorResponse;
import sc.com.assessment.cashman.dto.response.ImmutableErrorResponse;
import sc.com.assessment.cashman.exception.CashManException;

import java.util.Collections;

import static org.slf4j.LoggerFactory.getLogger;

@ControllerAdvice
public class CashManExceptionHandler extends BaseExceptionHandler {

    private static final Logger LOG = getLogger(DenominationsNotAvailableExceptionHandler.class);

    @ExceptionHandler(CashManException.class)
    public ResponseEntity<ErrorResponse> defaultErrorHandler(final CashManException ex) {
        LOG.warn("InsufficientCashException propagated to Controller Advice {}", ex.getMessage());

        final ErrorResponse errorResponse = ImmutableErrorResponse.builder().errors(Collections.singleton(
                ImmutableErrorData.builder()
                        .code(ex.getErrorCode())
                        .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .detail(ex.getMessage())
                        .build()
                )
        ).build();

        return standardErrorResponse(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
