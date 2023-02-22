package app.kakaobank.task.support.domain;

public interface ExplainableMessage {

    int getCode();
    String getMessage();
    int getStatus();
}
