package app.kakaobank.task.api.parseschool.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "로그인 요청 request")
@Getter
@AllArgsConstructor
public class ParseSchoolRequest {

    private String path;

    private MultipartFile excelFile;
}
