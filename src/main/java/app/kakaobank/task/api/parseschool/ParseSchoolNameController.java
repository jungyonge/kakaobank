package app.kakaobank.task.api.parseschool;


import app.kakaobank.task.api.parseschool.reqeust.ParseSchoolRequest;
import app.kakaobank.task.api.parseschool.response.ParseSchoolResponse;
import app.kakaobank.task.module.parseschool.applicaion.ParseSchoolNameHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parse")
public class ParseSchoolNameController {

    private final ParseSchoolNameHandler parseSchoolHandler;
    private final String excelLocation;


    public ParseSchoolNameController(ParseSchoolNameHandler parseSchoolHandler,
        @Value("${spring.servlet.multipart.location}") String excelLocation) {
        this.parseSchoolHandler = parseSchoolHandler;
        this.excelLocation = excelLocation;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ParseSchoolResponse> parseSchoolName(
        @ModelAttribute ParseSchoolRequest request)  {

        var result = parseSchoolHandler.parseSchoolName(request.getExcelFile(), request.getPath());

        return ResponseEntity.ok(result);
    }
}
