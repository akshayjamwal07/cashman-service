package sc.com.assessment.cashman.exception;

import sc.com.assessment.cashman.exception.handler.ErrorCode;
import sc.com.assessment.cashman.exception.handler.ErrorCoded;

public class InsufficientCashException extends RuntimeException implements ErrorCoded {

    private static final long serialVersionUID = 2673599595660874292L;

    private final String errorCode = ErrorCode.INSUFFICIENT_CASH.getCode();

    public InsufficientCashException(final String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
