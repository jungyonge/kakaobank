package app.kakaobank.task.module.parseschool.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParseLogStatus {

    FAIL("FAIL", "실패"),
    SUCCESS("SUCCESS", "성공"),
    ;

    private final String value;
    private final String desc;

}
