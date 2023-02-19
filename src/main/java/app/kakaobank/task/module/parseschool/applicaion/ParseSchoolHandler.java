package app.kakaobank.task.module.parseschool.applicaion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ParseSchoolHandler {


    public void parseSchoolName(MultipartFile excelFile){

        var list = new ArrayList<String>();

        readToList(excelFile);
    }

    public static List<String> readToList(MultipartFile excelFile) {
        List<String> list = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(excelFile.getInputStream()));
            Charset.forName("UTF-8");
            String line = "";
            StringBuilder sb = new StringBuilder();

            while((line=br.readLine()) != null) {
                if(line.startsWith("\"")){
                    sb = new StringBuilder();
                }
                sb.append(line);
                if(line.endsWith("\"")){
                    list.add(sb.toString());
                }
            }

        } catch (FileNotFoundException e) {
//            logger.error(e);
        } catch (IOException e) {
//            logger.error(e);
        } finally {
            try {
                if(br != null) {br.close();}
            } catch (IOException e) {
//                logger.error(e);
            }
        }
        return list;
    }
}
