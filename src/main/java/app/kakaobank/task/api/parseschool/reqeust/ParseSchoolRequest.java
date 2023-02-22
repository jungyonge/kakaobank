package app.kakaobank.task.api.parseschool.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "파싱 요청 request")
@Getter
@AllArgsConstructor
public class ParseSchoolRequest {

    @Schema(description = "결과물 저장할 path", example = "/Users/jungyong/Downloads")
    private String path;

    @Schema(description = "파싱할 데이터")
    private MultipartFile file;
}
