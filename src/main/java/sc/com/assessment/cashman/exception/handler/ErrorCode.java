package sc.com.assessment.cashman.exception.handler;

public enum ErrorCode {

    INSUFFICIENT_CASH("error.client.insufficientFunds"),
    INTERNAL_SERVER_ERROR("error.server.internalServerError"),
    DENOMINATIONS_UNAVAILABLE("error.client.denominationsUnavailable");

    private final String code;

    ErrorCode(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
