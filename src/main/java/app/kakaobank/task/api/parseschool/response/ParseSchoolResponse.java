package app.kakaobank.task.api.parseschool.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "파싱 성공 response")
@Getter
@AllArgsConstructor
public class ParseSchoolResponse {

    @Schema(description = "파싱 성공여부")
    private boolean status;

    @Schema(description = "결과물 저장한 위치")
    private String path;

    @Schema(description = "파싱 시도 건수")
    private long totalParseCount;

    @Schema(description = "파싱 성공 건수")
    private int successParseCount;

    @Schema(description = "파싱 실패 건수")
    private int failParseCount;


}
