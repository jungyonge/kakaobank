package app.kakaobank.task.domain;

public class DomainValidationException extends RuntimeException {

    private final int code;
    private final int httpStatus;

    public DomainValidationException(ExplainableMessage explainableMessage) {
        super(explainableMessage.getMessage());
        this.code = explainableMessage.getCode();
        this.httpStatus = explainableMessage.getStatus();
    }

    public int getCode() {
        return this.code;
    }

    public int getHttpStatus() {
        return this.httpStatus;
    }

}
