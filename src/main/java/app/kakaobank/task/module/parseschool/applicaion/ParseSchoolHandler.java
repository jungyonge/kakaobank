package app.kakaobank.task.module.parseschool.applicaion;

import app.kakaobank.task.support.CsvFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ParseSchoolHandler {

    private final CsvFileUtil csvFileUtil;

    public ParseSchoolHandler(CsvFileUtil csvFileUtil) {
        this.csvFileUtil = csvFileUtil;
    }

    public void parseSchoolName(MultipartFile excelFile) {

        var list = csvFileUtil.readToList(excelFile);

        for (String reply : list) {
            String pure = refineReply(reply);
        }
    }

    private String refineReply(String reply) {

        return reply.replaceAll("[^가-힣a-zA-Z-, ]", "");
    }

}
