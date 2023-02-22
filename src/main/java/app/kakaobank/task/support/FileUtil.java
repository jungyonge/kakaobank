package app.kakaobank.task.support;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileUtil {

    public List<String> readCsvToList(MultipartFile excelFile) {
        List<String> list = new ArrayList<>();
        int lineCnt = 0;
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(excelFile.getInputStream()))) {
            String line = "";
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (line.startsWith("\"")) {
                    sb = new StringBuilder();
                }
                sb.append(line).append(" ");
                if (line.endsWith("\"") && lineCnt != 0) {
                    list.add(sb.toString());
                }
                lineCnt++;
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    public boolean writeLog(String filePath, String fileName, String log) throws IOException {

        var file = new File(String.format("%s/%s", filePath, fileName));

        if (file.exists()) {
            var deleteFileResult = file.delete();

            if (!deleteFileResult) {
                logger.error("file delete fail");
                throw new IOException();
            }
        }

        try {
            var createFileResult = file.createNewFile();

            if (!createFileResult) {
                return false;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

        try (var writer = new BufferedWriter(new FileWriter(file))) {

            writer.write(log);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

        return true;
    }
}
