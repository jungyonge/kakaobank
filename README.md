## 카카오뱅크
### 요구사항
~~~
1. 과제 내용
첨부된 댓글리스트 파일에서 유효한 학교 이름을 찾아내고 학교별로 카운트해주세요.

2. 요구 및 제약 사항
개발 언어는 Java로 제한합니다. 외부 라이브러리 등의 사용은 Open Source 또는 무료의 경우 제한없이 사용 가능합니다.

3. 과제 제출 방식
- 결과는 result.txt, 로그는 result.log로 저장되어야 하고 그 형식은 다음과 같습니다.
  학교이름과 숫자 사이에는 탭문자가 들어갑니다.

출력
ㅇㅇ중학교      192
ㅇㅇㅇ고등학교 254
...

- 코드와 결과물을 같이 제출하셔야 합니다.
~~~

--- 
## 개발 환경
- 기본 환경
    - IDE: IntelliJ IDEA
    - OS: Mac
    - Git
- Server
    - Java11
    - Spring Boot 2.7.9
    - JPA
    - Gradle
    - H2

---
## 설명
- 정규식을 활용해 최대한 학교명 parse
- /api/v1/parse api로 요청
  - csv 파일과 결과를 저장할 path 파라미터필요 (아래 api 명세서확인)
- 파싱로직
  - 1차 정제로직을 거침
    - 불필요한 특수문자,단 제거
  - 정규식을 활용하여 학교이름추출
  - 추출된 학교이름이 함축어면 replace (ex : 여고 -> 여자고등학교)
  - 완성된 학교이름을 통해 count
- parseLog 테이블을 만들어서 파싱성공, 실패 log를 확인 가능하게 구성

---
## 빌드, 실행
~~~
//미리 생성한 결과물 확인
vi result.txt
vi result.log

//압축해제 후 디렉토리 이동
cd kakaobank

//빌드
./gradlew clean build

//디렉토리이동
cd build/libs

//실행
java -jar task-0.0.1-SNAPSHOT.jar

//swagger 접속
http://localhost:8080/swagger-ui/index.html

//h2-console 접속 
http://localhost:8080/h2-console/login.jsp
~~~
---
## 과제후기
- 정규식을 잘 쓰면 깔끔한 것 같다.
- 정상적으로 파싱하였지만 정규식으로 다 해결 못하는 부분이 있다.
  - 학교앞에 지역명이 있는 부분에 대한 개선점 고민해보자.(수원시율현중학교, 율현중학교)
  - 전체 list를 다시 검색하면 비효율적 같은데, 개선점 고민해봐야 겠다.
- 이러한 과제는 어떻게 테스트코드를 코딩해야 하는지 모르겠다.
---

## API 명세서
### 학교이름파싱 API

* **설명**

  학교이름파싱 API

* **URL**

  /api/v1/parse

* **Method:**

  `POST`

* **Data Params**

    - file (csv file) : 파싱할 파일
    - path (String) : 결과 저장할 디렉토리위치

* **Success Response:**

    * **Code:** 200 <br />
      **Content:**
~~~
{
    "status": true,
    "path": "/Users/jungyong/Downloads",
    "totalParseCount": 1000,
    "successParseCount": 981,
    "failParseCount": 19
}
~~~
* **Error Response:**

    * **Code:** 500 BAD REQUEST <br />
      **Content:**
~~~
{
    "errorCode": 10001,
    "errorMessage": "결과 파일 생성에 실패 했습니다."
}
~~~
