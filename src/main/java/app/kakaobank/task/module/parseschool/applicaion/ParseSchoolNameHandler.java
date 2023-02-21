package app.kakaobank.task.module.parseschool.applicaion;

import app.kakaobank.task.api.parseschool.response.ParseSchoolResponse;
import app.kakaobank.task.module.parseschool.domain.ParseLog;
import app.kakaobank.task.module.parseschool.domain.ParseLogRepository;
import app.kakaobank.task.module.parseschool.domain.ParseLogStatus;
import app.kakaobank.task.support.FileUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ParseSchoolNameHandler {

    private final FileUtil fileUtil;

    private final ParseLogRepository parseLogRepository;

    public ParseSchoolNameHandler(FileUtil fileUtil, ParseLogRepository parseLogRepository) {
        this.fileUtil = fileUtil;
        this.parseLogRepository = parseLogRepository;
    }

    public ParseSchoolResponse parseSchoolName(MultipartFile excelFile, String path) {

        var list = fileUtil.readCsvToList(excelFile);
        HashMap<String, Integer> map = new HashMap<>();
        List<ParseLog> parseLogs = new ArrayList<>();

        int failCount = 0;
        int successCount = 0;
        long replyId = 1;


        for (String reply : list) {
            ParseLog parseLog;
            String pureReply = refineReply(reply);
            String schoolName = parseSchoolName(pureReply);
            schoolName = modifySchoolName(schoolName).replaceAll(" ", "");

            if (schoolName.isEmpty() || !verifySchoolName(schoolName)) {
                parseLog = ParseLog.create(reply, schoolName, excelFile.getOriginalFilename(), replyId,
                    ParseLogStatus.FAIL);
                failCount++;
            } else {
                parseLog = ParseLog.create(reply, schoolName, excelFile.getOriginalFilename(), replyId,
                    ParseLogStatus.SUCCESS);
                map.put(schoolName, map.getOrDefault(schoolName, 0) + 1);
                successCount++;
            }
            parseLogs.add(parseLog);

            replyId++;
        }

        parseLogRepository.saveAll(parseLogs);

        List<String> keySet = new ArrayList<>(map.keySet());
        keySet.sort((o1, o2) -> map.get(o2).compareTo(map.get(o1)));
        StringBuilder sb = new StringBuilder();
        for (String key : keySet) {
            sb.append(key).append("\t").append(map.get(key)).append("\n");
        }

        return new ParseSchoolResponse(true, path, replyId - 1, successCount, failCount);

    }

    private String refineReply(String reply) {
        return reply.replaceAll("[^가-힣a-zA-Z-,. ]", " ")
            .replaceAll("(?:저희|우리|다른|진짜|제발요|하고|말고|치고)", "");
    }

    private String parseSchoolName(String reply) {
        Matcher matcher = SCHOOL_FULL_PATTERN.matcher(reply);
        String school = "";

        if (matcher.find()) {
            return matcher.group("school");
        }

        matcher = SCHOOL_SHORT_PATTERN.matcher(reply);

        if (matcher.find()) {
            return matcher.group("school");
        }

        return school;
    }

    private String modifySchoolName(String schoolName) {
        if (schoolName.endsWith("여대")) {
            return schoolName.replace("여대", "여자대학교");
        }
        if (schoolName.endsWith("여고")) {
            return schoolName.replace("여고", "여자고등학교");
        }
        if (schoolName.endsWith("남고")) {
            return schoolName.replace("남고", "남자고등학교");
        }
        if (schoolName.endsWith("체고")) {
            return schoolName.replace("체고", "체육고등학교");
        }
        if (schoolName.endsWith("여중")) {
            return schoolName.replace("여중", "여자중학교");
        }
        if (schoolName.endsWith("남중")) {
            return schoolName.replace("남중", "남자중학교");
        }
        if (schoolName.endsWith("대학")) {
            return schoolName.replace("대학", "대학교");
        }
        if (schoolName.endsWith("대")) {
            return schoolName.replace("대", "대학교");
        }
        if (schoolName.endsWith("고")) {
            return schoolName.replace("고", "고등학교");
        }
        if (schoolName.endsWith("중")) {
            return schoolName.replace("중", "중학교");
        }
        if (schoolName.endsWith("초")) {
            return schoolName.replace("초", "초등학교");
        }
        return schoolName;
    }

    private boolean verifySchoolName(String school) {
        int length = school.replaceAll(SCHOOL_NAME_PATTERN, "").length();
        return length > 1 && length < 20;
    }


    private static final Pattern SCHOOL_FULL_PATTERN = Pattern.compile(
        "(?<school>"
            + "[가-힣 ]?[가-힣]*국제학교|"
            + "[가-힣 ]?[가-힣]*(?:초등학교|초 등학교|초등 학교|초등학 교|초 등 학교|초 등 학 교)|"
            + "[가-힣 ]?[가-힣]*(?: 남자 중학교|남자중학교|남자중 학교|남자중 학 교|남자중학 교)|"
            + "[가-힣 ]?[가-힣]*(?: 여자 중학교|여자중학교| 여자중학교| 여자중 학교|여자중 학교|여자중 학 교|여자중학 교)|"
            + "[가-힣 ]?[가-힣]*(?: 중학교|중학교|중 학교|중 학 교|중학 교)|"
            + "[가-힣 ]?[가-힣]*(?: 남자고등학교|남자고등학교|남자고 등학교|남자고등 학교|남자고등학 교|남자고 등 학교|남자고 등 학 교)|"
            + "[가-힣 ]?[가-힣]*(?: 여자[a-zA-Z]*고등학교| 여자고등학교|여자고등학교|여자고 등학교|여자고등 학교|여자고등학 교|여자고 등 학교|여자고 등 학 교| 서여자고등학교| 여자 고등학교| 동여자중학교| 여자상업고등학교)|"
            + "[가-힣 ]?[가-힣]*(?: 고등학교|고등학교|고 등학교|고등 학교|고등학 교|고 등 학교|고 등 학 교|체고| 관광고등학교)|"
            + "[가-힣 ]?[가-힣]*(?: 대학교|대학교|대 학교|대 학 교|대학 교)"
            + ")");

    private static final Pattern SCHOOL_SHORT_PATTERN = Pattern.compile(
        "(?<school>"
            + "[가-힣]+초|"
            + "[가-힣]+중|"
            + "[가-힣]+고|"
            + "[가-힣]+대"
            + ")");

    private static final String SCHOOL_NAME_PATTERN = "(?:여자[a-zA-Z]*고등학교|여자고등학교|남자고등학교|체육고등학교|고등학교|초등학교|여자중학교|남자중학교|중학교|대학교|여자대학교)";

}
