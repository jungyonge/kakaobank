package app.kakaobank.task.module.parseschool.applicaion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class ParseSchoolHandlerTest {


    @Test
    void test(){
        String pure = "우리중을 말씀드리자면 밥으로 시작해 밥으로 끝나는 하루입니다 저희는 학교가자마자 급식표를봅니다 교시에도봅니다 쉬는시간에도 똥싸는중에도  거짓말같죠 저희중은 원래 이럽니다 급식먹으러갈때 진짜전쟁납니다 진짜 레알 다른중보다 진짜 세계 대전 일어날꺼같아요 진짜 저희중은  이시간을위해 삽니다 배달의 민족님 동두천 여자 중 학교한테 자장면 그릇 부탁드립니다 배달의 민족한테 축복을석보현보이나연나김은서은이채은채은솔은오승윤승정찬별찬박채연채최재은재이서진서임혜진혜혜지혜백보경보민혜민박아현아유채린채이윤경윤박수민수배수민수이가영가영가영 \n";
        pure = refineReply(pure);
        String school = parseSchool(pure);
        System.out.println(school);
    }

    private String refineReply(String reply) {

        return reply.replaceAll("[^가-힣a-zA-Z-, ]", "").replaceAll("[우리|저희|다른]", "");
    }
    private String parseSchool(String reply){
        Matcher matcher = SCHOOL_PATTERN.matcher(reply);
        String school = "";

        if (matcher.find()) {
            System.out.println(matcher.groupCount());
            if(matcher.groupCount() > 1){
                for(int i = 0 ; i < matcher.groupCount(); i ++){
                    school = matcher.group(i);
                }
            }else {
                school = matcher.group("schoolfull");
            }
        }

        return school;
    }


    public static final Pattern SCHOOL_PATTERN = Pattern.compile(
        "(?<schoolfull>"
            + "[가-힣]+ *(?:여자|남자)? *(?: 초등학교| 초 등학교| 초등 학교| 초등학 교| 초 등 학교| 초 등 학 교)"                   // e.g. (울산광역시 중구) 만남의거리
            + "[가-힣]+ *(?:여자|남자)? *(?:중학교|중 학교|중 학 교|중학 교)"                   // e.g. (울산광역시 중구) 만남의거리
            + "[가-힣]+ *(?:여자|남자)? *(?: 고등학교| 고 등학교| 고등 학교| 고등학 교| 고 등 학교| 고 등 학 교)"                   // e.g. (울산광역시 중구) 만남의거리
            + "[가-힣]+ *(?:여자|남자)? *(?: 대학교| 대 학교| 대 학 교| 대학 교)"                   // e.g. (울산광역시 중구) 만남의거리
            + ")"
            + "?"                   // e.g. (울산광역시 중구) 만남의거리
            + "(?<schoolshort>"
            + "[가-힣]+초|"                   // e.g. (울산광역시 중구) 만남의거리
            + "[가-힣]+중|"                   // e.g. (울산광역시 중구) 만남의거리
            + "[가-힣]+고|"                   // e.g. (울산광역시 중구) 만남의거리
            + "[가-힣]+대"                   // e.g. (울산광역시 중구) 만남의거리
//
//
//            + "[가-힣]+[읍|면]{1}[가-힣0-9·.-]+로|"  // e.g. (제주특별자치도 서귀포시) 남원읍 태위로
//            + "[가-힣]+[읍|면]{1}[가-힣0-9·.-]+길|"  // e.g. (울산광역시 울주군) 산남면 동향교1길
//            + "[가-힣0-9·.-]+로{1}[가-힣0-9·.-]+길|"  // e.g. (서울특별시 강남구) 강남대로54길
//            + "[가-힣0-9·.-]+길|"                    // e.g. (서울특별시 종로구) 경교장1길
//            + "[가-힣]-[0-9]·.-]+길|"              // e.g. (서울특별시 강남구) 강남대로-54길
//            + "[가-힣0-9·.-]+로|"                   // e.g. (서울특별시 중랑구) 동일로
            + ")");

}