package app.kakaobank.task.domain;

public interface ExplainableMessage {

    int getCode();
    String getMessage();
    int getStatus();
}
