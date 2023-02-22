package app.kakaobank.task.config.web;

import app.kakaobank.task.support.domain.DomainValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalControllerErrorAdvice {

    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<ErrorResponse> domainValidationException(DomainValidationException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.error(e.getCode(), e.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

}
