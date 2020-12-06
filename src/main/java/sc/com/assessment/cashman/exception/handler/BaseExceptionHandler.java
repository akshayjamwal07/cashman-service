package sc.com.assessment.cashman.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sc.com.assessment.cashman.dto.response.ErrorResponse;


public class BaseExceptionHandler {

    protected ResponseEntity<ErrorResponse> standardErrorResponse(final ErrorResponse errorResponse,
                                                                  final HttpStatus httpStatus) {
        return new ResponseEntity<>(errorResponse, httpStatus);

    }
}
