package app.kakaobank.task.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class CsvFileUtil {

    public List<String> readToList(MultipartFile excelFile) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(excelFile.getInputStream()))) {
            String line = "";
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (line.startsWith("\"")) {
                    sb = new StringBuilder();
                }
                sb.append(line).append(" ");
                if (line.endsWith("\"")) {
                    list.add(sb.toString());
                }
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return list;
    }
}
