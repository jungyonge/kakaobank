package app.kakaobank.task.config.web;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private final int errorCode;
    private final String errorMessage;

    public static ErrorResponse error(int errorCode, String errorMessage) {
        return ErrorResponse
                .builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }
}
