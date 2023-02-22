package app.kakaobank.task.module.parseschool.applicaion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import app.kakaobank.task.module.parseschool.domain.ParseLog;
import app.kakaobank.task.module.parseschool.domain.ParseLogRepository;
import app.kakaobank.task.support.FileUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class ParseSchoolNameHandlerTest {

    @Mock
    private FileUtil fileUtil;
    @Mock
    private ParseLogRepository parseLogRepository;
    @InjectMocks
    private ParseSchoolNameHandler parseSchoolNameHandler;
    @Captor
    ArgumentCaptor<ParseLog> logArgumentCaptor;


    @Test
    @DisplayName("댓글 파싱 테스트")
    void parseSchoolName_Success() throws IOException {

        //given
        List<String> list = new ArrayList<>();
        list.add("수원, 창현고 짜장면 먹고싶어요\n"
            + "사진은 제가 먹다남은 짬뽕이네요\n"
            + "재학중은 아니지만 모교학생분들의 배를 불러드리게 하고자 이 이벤트에 참여하는 바입니다\n"
            + "(팔로워님들 좋아요 한번씩 부탁합니다..)");
        list.add("울산 문수고등학교입니다 학교다니면서 잘다니지도않았지만 학교에 기여한게없어서 참여합니다\n"
            + "졸업하기전에 사람구실한번하고싶네요\n"
            + "(댓글좋아요 1등하면 짜장면준다니까 눌러주세요)\n"
            + "제꺼눌러야되요 다른거말고");
        list.add("대구, 대구여자상업고등학교 자장면 먹고싶어요!!!!!\n"
            + "취업/진학으로 스트레스를 많이 받는 우리학교학생들에게 한그릇의 자장면으로 힐링하게 해주세요~❤️❤️❤️ \n"
            + "저번 치킨이벤트 안타깝게 2위했어요..?\n"
            + "이번엔 꼭 되길!! 배민애용합니당>_<??");

        final String fileName = "testFile.csv";
        final String contentType = ".csv";
        final String filePath = "/test";

        MockMultipartFile file = new MockMultipartFile(
            "test",
            fileName,
            contentType,
            "test".getBytes(StandardCharsets.UTF_8)
        );

        doReturn(list).when(fileUtil).readCsvToList(file);

        //when
        parseSchoolNameHandler.parseSchoolName(file, filePath);
        verify(parseLogRepository, Mockito.times(3)).save(logArgumentCaptor.capture());
        List<ParseLog> logs = logArgumentCaptor.getAllValues();

        //then
        assertEquals("창현고등학교", logs.get(0).getParseResult());
        assertEquals("문수고등학교", logs.get(1).getParseResult());
        assertEquals("대구여자상업고등학교", logs.get(2).getParseResult());


    }

}