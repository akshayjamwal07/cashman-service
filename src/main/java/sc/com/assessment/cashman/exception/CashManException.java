package sc.com.assessment.cashman.exception;

import sc.com.assessment.cashman.exception.handler.ErrorCode;
import sc.com.assessment.cashman.exception.handler.ErrorCoded;

public class CashManException extends RuntimeException implements ErrorCoded {

    private static final long serialVersionUID = 3560075948264935347L;

    private final String errorCode = ErrorCode.INTERNAL_SERVER_ERROR.getCode();

    public CashManException(final String message, final Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
