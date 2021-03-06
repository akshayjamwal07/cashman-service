package sc.com.assessment.cashman.exception;

import sc.com.assessment.cashman.exception.handler.ErrorCode;
import sc.com.assessment.cashman.exception.handler.ErrorCoded;

public class DenomationsNotAvailableException extends RuntimeException implements ErrorCoded {

    private static final long serialVersionUID = 2673599595660874291L;

    private final String errorCode = ErrorCode.DENOMINATIONS_UNAVAILABLE.getCode();

    public DenomationsNotAvailableException(final String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
