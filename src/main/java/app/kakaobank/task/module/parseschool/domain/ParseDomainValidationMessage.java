package app.kakaobank.task.module.parseschool.domain;

import app.kakaobank.task.support.domain.ExplainableMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ParseDomainValidationMessage implements ExplainableMessage {

    ERROR_CREATE_RESULT(1_0001, "결과 파일 생성에 실패 했습니다."),

    ;

    private final int code;
    private final String message;
    private final HttpStatus status;

    ParseDomainValidationMessage(int code, String message) {
        this.code = code;
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public int getStatus() {
        return status.value();
    }
}
