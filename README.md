# too-much-talker-main-server

개요
=============

쓰기 막막한 **자기소개서**, 누가 대신 써준다면 얼마나 좋을까요? 아니, 초안이라도 작성해준다면 더 바랄것도 없을 것 같습니다. 이 답답함을 해결하기 위해 **Chat GPT**를 이용해 보기로 했습니다. 하지만 GPT에게 그냥 자기소개서를 작성해달라고 하면 합격할 수 있는 자기소개서를 써주지는 않죠. 그래서 어떻게 **프롬포트**를 작성해야 할지 고민하였습니다. 그리고 저희의 고민과 경험을 **서비스**로 담아내어 여러 사람들에게 **공유**할 수 있도록 만들었습니다.

본 프로젝트는 크게 두 파트로 나뉘어 진행되었습니다. 하나는 서비스 구현을 위한 작업들이고 다른 하나는 GPT의 성능을 개선하기 위한 프롬포트 엔지니어링 작업들입니다. 각 파트별로 어떤 작업이 이뤄졌는지 궁금하신 분들은 아래를 참고하시기 바랍니다. (프로젝트 종료 후 작성 예정)

시연 영상 : https://youtu.be/KvNnh40It4s

**🖊️ Language**

Typescript, HTML, Css, Tailwind Python

**📚 Library**

React, Typescript 
Python : langchain, …

**🦜 Members**

**원가연**, Back-End Developer, henzel1013@gmail.com  
**차유진**, Prompt Engineer, chaujin00@gmail.com  
**길하균**, Project Manager + Prompt Engineer, ghaguniv@gmail.com  
**최용현**, Front-End Developer, chldydgus777@kakao.com  


주요 테스크 및 트러블슈팅
=============

### **테스크명 :** API 명세서 작성하기


**테스크 설명**

- 어떤 API가 있는지 볼 수 있도록 API 명세를 제공하기.

**고민 or 이슈&해결**

- 기존의 API 명세 작성은 이런식으로 특정 API에 따라 request 데이터와 return값으로 오는 데이터가 무엇인지 문서로 작성하여 프론트개발자와 소통한다.

    ![image](https://github.com/gpt-web-project/too-much-talker-main-server/assets/57881683/dfe2ffa6-9d79-4d01-925c-7f069176ce25)

    
    그런데 이 방법의 경우 실시간으로 요청사항이 바뀌거나 코드가 바뀌는 경우 추적이 불가능하며 
    API 명세를 봐야하는 클라이언트 입장에선 테스트도 못해보니 불편함이 많다.
    
    그래서 서버 Swagger라는 API 명세 라이브러리를 적용시킨뒤
    
    ```jsx
    @Configuration
    @EnableSwagger2
    @EnableAsync
    @EnableWebMvc
    public class SwaggerConfiguration implements WebMvcConfigurer {
    
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    
        @Bean
        public Docket swagger() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build();
        }
    }
    ```
    
    각각의 API 컨트롤러에서 해당 API가 어떤 역할인지 코드로 작성해주면 된다.
    
    ```jsx
        @ApiOperation(value="로그인 API"
                , notes="토큰 로그인은 추후 구현하는걸로합니당. 일단은 브라우저에 유저아이디값 저장하는걸로" +
                ", 추후 요청을 너무 많이 보내는 유저는 벤먹이기위해 최초 로그인 ip도 같이보내주세요"
                , response = LoginResponse.class)
        @PostMapping("/login")
        private ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest){
            LoginResponse user = accountService.login(loginRequest);
            return new ResponseEntity(
                    user,
                    HttpStatus.OK
            );
        }
    ```
    
    그러면 자동으로 swagger config에서 @ApiOperation을 사용하는 API를 추적해 이렇게  웹페이지에서 테스트해줄수 있는 형태로 제공한다. → 서버를 켜면 해당 swagger 웹페이지도 생성됨

  ![image](https://github.com/gpt-web-project/too-much-talker-main-server/assets/57881683/04969c68-616f-4b44-935f-f83b06f7612a)

    
    이건 실제로 테스트해볼수있는 주소다
    
    [http://43.202.47.122:8080/swagger-ui/#/사용자 계정 관련 기능/loginUsingPOST](http://43.202.47.122:8080/swagger-ui/#/%EC%82%AC%EC%9A%A9%EC%9E%90%20%EA%B3%84%EC%A0%95%20%EA%B4%80%EB%A0%A8%20%EA%B8%B0%EB%8A%A5/loginUsingPOST)
    


### **테스크명 :** Cors에러 해결 뒤 Swagger 라이브러리 충돌


**테스크 설명**

- cors 오류라는게 존재한다. 간단하게 설명하자면 클라이언트의 주소와 API 서버 주소가 다르면 클라이언트에서 다른 서버의 리소스라고 생각해 해당 서버의 리소스를 제공하지 않는다는거다.
즉, 웹서버도 리소스이기에 내가 배포한 API서버를 프론트 서버에서 요청하지못하는 문제가 발생했다.

    ![image](https://github.com/gpt-web-project/too-much-talker-main-server/assets/57881683/6be9fb2a-f996-43d2-ad46-ce6cc124459c)

    

+) 이건 추가자료

[Cors 오류 왜 생기는걸까?](https://lets-do-the-odessey.tistory.com/54)


**고민 or 이슈&해결**

- GPT의 조언에 따르면 리소스를 제공하는 서버에서 **Access-Control-Allow-Origin** 라는 헤더값만 적절히 제공하면됐다.
    
    ```jsx
    기본적으로 웹 브라우저는 같은 출처 정책(Same-Origin Policy)을 따릅니다. 
    이 정책에 따라, 웹 페이지는 같은 도메인에서만 리소스를 요청할 수 있습니다. 
    예를 들어, https://example.com에서 실행 중인 스크립트는 기본적으로 https://example.com의 리소스만 요청할 수 있습니다. 다른 도메인(예: https://another-domain.com)의 리소스를 요청하려면, 그 리소스가 CORS 헤더를 통해 명시적으로 권한을 부여해야 합니다.
    ```
    
    이번 프로젝트에서 사용한 Spring 프레임워크는 이러한 기능이 잘 세팅되어있어서 추가해줬는데
    
    ```jsx
    @Configuration
    public class WebConfiguration implements WebMvcConfigurer {
        // Todo. 나중에 도메인 생기면 그때는 출처 제한하도록
    
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")//cors를 적용할 URL패턴 정의
                    .allowedOriginPatterns("*")//자원 공유 허락할 Origin 허락
                    .allowedMethods("*")//허락할 HTTP method 지정
                    .allowCredentials(true)
                    .allowedHeaders("*");
        }
    }
    ```
    
    문제는 이걸 추가하고나니까 위에서 제공한 API 명세 페이지가 오류 나면서 안뜨는거다.
    
![image](https://github.com/gpt-web-project/too-much-talker-main-server/assets/57881683/6bd98619-b144-4026-9be0-05089220f2c1)


<aside>
⚠️ 생성된 에러메세지

Unable to infer base url. This is common when using dynamic servlet registration or when the API is behind an API Gateway. The base url is the root of where all the swagger resources are served. For e.g. if the api is available at http://example.org/api/v2/api-docs then the base url is http://example.org/api/. Please enter the location manually:

</aside>

해당 에러메세지를 따라 GPT에게 물어보거나 나와 똑같은 에러가 난 사람들이 해결한 경우는 대부분

- Swagger 설정에 base URL을 수동으로 설정하는 부분을 추가
- Security 문제(이건 다른 라이브러리와 충돌인 경우)
- 라이브러리끼리 버전을 맞추지 않은 경우

그런데 위 세개 다 해당되지않는 상황이었다..

그러던 도중 굉장히 허무하게.. 라이브러리가 중복설치되어 오류가 났던상황이란걸 깨달았다.

설명하자면 springfox:starter 라는 라이브러리안에 swagger와 swagger-ui가 포함되어있는데 이걸 따로 설치받아 오류가 났던것같다.(정확히 왜 충돌이 난건진 모르겠음..)

![image](https://github.com/gpt-web-project/too-much-talker-main-server/assets/57881683/e4430cb0-8b7e-4109-a7cc-76b25d0a6ec0)


### **테스크명 :** gpt 프롬프트 코드를 API로 동작하게 하기


**테스크 설명**

- 이 부분이 제일 힘들었던 부분인데 우리 프로젝트는 이런식으로 API 서버와 GPT에 요청하는 프롬프트 서버가 따로 나뉘어져있다.
그래서 프롬프트를 개발하는 분 1명 API서버(자바 서버)를 개발하는 나 1명 총 2명이서 백엔드개발을했는데,
프롬프트로 요청을 보내는 코드만 작성되어있었고 이걸 자바서버에서 요청 가능하게 API 형태로 만들어야했다.

![image](https://github.com/gpt-web-project/too-much-talker-main-server/assets/57881683/0cc2a10f-0f32-4ca9-9b54-59824c2293b4)


**고민 or 이슈&해결**

- 이게 api key와 이전에 다른 함수들로 합쳐진 메세지를 gpt에게 최종 요청 보내는 코드인데 이게 예상대로 되지않아(예외처리 되지않음, 디코딩 문제 및 라이브러리 버전 문제등)

```python
def get_completion(openai_api_key, messages):
    '''
    ==============================================
    api로 요청 보내고 답변 받아오는 함수
    ::openai_api_key:: openai api key(유료) 입력
    ::messages:: api로 전송할 system/user prompt입력
    ==============================================
    '''
    client = openai_api_key
    completion = client.chat.completions.create(
        model="gpt-4-1106-preview",
        messages=messages
    )
    return completion.choices[0].message
```

이렇게 같은 동작을 하는 version2 함수를 만들었다.

```python
def get_completion_v2(messages):
    # Todo. 인코딩 문제 해결
    # OpenAI API 키 설정
    openai.api_key = config.API_KEY

    # ChatCompletion 호출
    completion = openai.ChatCompletion.create(
        model="gpt-4-1106-preview",
        messages=messages
    )

    # 반환된 메시지를 가져옴
    message = completion
    message_jsonify = message.json()['choices'][0]['message']['content']

    # 'content' 필드 존재 여부 확인
    if 'content' not in message:
        # 'content' 필드가 없는 경우 예외 발생
        raise Exception("Content field is missing from the response.")

    # 'content' 필드가 있는 경우, 해당 값을 디코드
    print(message_jsonify)
    # decoded_content = message['content'].encode('utf-8').decode('unicode_escape')
    return message_jsonify
```

또한 우리는 데이터를 테이블에서 꺼낸 뒤 → 다시 해당 테이블에 commit해주는 코드를 작성해야했기에
이렇게 user_id값을 파라미터로 받으면 해당 user가 질문을 작성했지만 답변되지않은 질문이 있는지 검사 후,
해당 질문에 따른 유저의 정보값을 들고 온 뒤 위에 있는 get_completion_v2로 요청을 보내는 서비스로직을 작성해줬다.

```python
def generate_job_application_responses(user_id):
    # UserJobApplicationInfo 조회, 가장 최신값 한 개만 조회
    application_info = UserJobApplicationInfo.query.filter_by(user_id=user_id).order_by(
        UserJobApplicationInfo.created_at.desc()).first()

    if not application_info:
        raise ValueError("자기소개서 작성 요청을 해주세요.")

    unanswered_questions = JobApplicationQuestions.query.filter(
        JobApplicationQuestions.job_id == application_info.id,
        JobApplicationQuestions.answer == None
    ).all()

    if not unanswered_questions:
        raise ValueError("모든 질문에 대한 답변이 완료되었습니다.")

    # ExperienceHistory와 ExperienceDetails 조회 및 매핑
    experience_histories = ExperienceHistory.query.filter(
        ExperienceHistory.resume_id == application_info.id
    ).all()

    experiences_info = []
    for history in experience_histories:
        details = ExperienceDetails.query.filter(
            ExperienceDetails.experience_id == history.id
        ).all()

        experience_info = {
            "institution_name": history.institution_name,
            "position": history.position,
            "start_date": history.start_date.strftime('%Y-%m-%d'),
            "end_date": history.end_date.strftime('%Y-%m-%d') if history.end_date else '현재',
            "type": history.type,
            "description": [detail.description for detail in details],
            "skill_highlighted": [detail.skill_highlight for detail in details]
        }
        experiences_info.append(experience_info)

    # 질문에 대한 GPT 요청 및 결과 처리
    for question in unanswered_questions:
        # 경험 요약 생성
        experience_history = format_experiences_for_gpt(experiences_info)
        system_prompt_example = '아래의 지원자 정보와 기업 정보를 기반으로 자기소개서 질문에 답변해줘. 단, 말투는 평서문,경어체로 부탁해.'

        prompt_example = fill_info(
            company_name=application_info.company_name,
            job_title=application_info.job_title,
            job_description=application_info.job_description,
            qualification_requirements=application_info.qualification_requirements,
            question=question.question,
            experience_history=experience_history
        )

        print(" -- GPT 답변 요청 --")
        answer = get_completion_v2(get_messages(system_prompt_example,prompt_example))
        print(" -- GPT 답변 받음 --")

        # 받아온 답변을 JobApplicationQuestions 모델에 저장
        update_question_answer(question.id, answer)
```

### **테스크명 :** 파이썬 서버로 1초마다 user값 확인하는 배치 만들기

---

**테스크 설명**

- 그렇지만 자바만 쓰던 나는.. 파이썬 서버가.. 파이썬 프로젝트가.. 이렇게 많은 용량을 차지하는줄 몰랐다. 지금까지는 AWS에서 제공하는 공짜 메모리1기가 하드8기가짜리 가상PC에 배포해왔다.


![image](https://github.com/gpt-web-project/too-much-talker-main-server/assets/57881683/def0aa6b-9f1b-4e32-8cde-87b3f4dff3c0)


그런데 파이썬설치하고 운영체제가 깔려있고..그러니 무료로 제공되는  파이썬 라이브러리를 설치받을수가없었다..

<aside>
⚠️ ec2 서버에 뜬… 에러메세지

INFO: pip is looking at multiple versions of transformers to determine which version is compatible with other requirements. This could take a while.
Collecting transformers
Using cached transformers-4.38.0-py3-none-any.whl (8.5 MB)
Using cached transformers-4.37.2-py3-none-any.whl (8.4 MB)
Downloading transformers-4.37.1-py3-none-any.whl (8.4 MB)
|██████▋                         | 1.7 MB 4.4 MB/s eta 0:00:02ERROR: Could not install packages due to an OSError: [Errno 28] No space left on device

</aside>

**고민 or 이슈&해결**

- 아직 해결..중이다..^—^
