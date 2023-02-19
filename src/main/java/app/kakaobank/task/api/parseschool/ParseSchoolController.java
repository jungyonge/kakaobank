package app.kakaobank.task.api.parseschool;


import app.kakaobank.task.api.parseschool.reqeust.ParseSchoolRequest;
import app.kakaobank.task.api.parseschool.response.ParseSchoolResponse;
import app.kakaobank.task.module.parseschool.applicaion.ParseSchoolHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parse")
public class ParseSchoolController {

    private final ParseSchoolHandler parseSchoolHandler;
    private final String excelLocation;


    public ParseSchoolController(ParseSchoolHandler parseSchoolHandler,
        @Value("${spring.servlet.multipart.location}") String excelLocation) {
        this.parseSchoolHandler = parseSchoolHandler;
        this.excelLocation = excelLocation;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ParseSchoolResponse> parseSchoolName(
        @ModelAttribute ParseSchoolRequest request) throws IOException {

//        var directoryName = System.nanoTime();
//
//        Path path = Paths.get(String.format("%s/%d", excelLocation, directoryName));
//        Files.createDirectories(path);
//
//        var excelFile = new File(String.format("%s/%d/_Reply.xlsx", excelLocation, directoryName));
//        request.getExcelFile().transferTo(excelFile);

        parseSchoolHandler.parseSchoolName(request.getExcelFile());

        return ResponseEntity.ok(new ParseSchoolResponse());
    }
}
