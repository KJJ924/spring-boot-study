# Logging



spring-boot 은 기본적으로 Logback 이 로깅에 사용된다.

![image](https://user-images.githubusercontent.com/64793712/104757536-830d4180-57a0-11eb-95c2-c32da298ecb4.png)




Logback 은 SLF4J 의 구현체 이며 자바 오픈소스 로깅 프레임워크이다.



LogBack을 xml configration 이용할때 3가지의 주요 설정이 있는데 다음과같다. 



상세 설정은(http://logback.qos.ch/manual/index.html) 참고



1. **Appender**

   로그 메세지를 출력할 대상을 결정함 (console , file,DB  등).

2. **Encoder**

   로그 메세지 의 포멧을 지정함 .

3. **Logger**, **root**

   appender 를 읽어 로그를 출력할 package와  log level 을 설정 함.



#### Example

```xml
<configuration>

  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <!-- encoders are assigned the type
         ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
  </appender>

  <logger name="chapters.configuration" level="INFO"/>

  <!-- Strictly speaking, the level attribute is not necessary since -->
  <!-- the level of the root level is set to DEBUG by default.       -->
  <root level="DEBUG">          
    <appender-ref ref="STDOUT" />
  </root>  
  
</configuration>
```



예제를 분석해보자면   

1.  STDOUT 의 `ConsoleAppender` 임으로 log를  콘솔창에 출력

2.  STDOUT 의 로그 메세지의 <u>출력 포맷</u>은  `%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n` 다음과 같음.

3.  chapters.configuration 패키지 의 로그를 <u>INFO 레벨</u>로 출력.

4.  전체패키지(root)  는 STDOUT appender 를 참고하여 DEBUG 레벨로  로그 출력.

   



이제 Spring-boot 에서 LogBack을 설정하는방법을 알아보자.



spring-boot 에서는 application.properties 에서 간단히 설정이가능하다.



1. **패키지별 로그 레벨 수준 설정**

   ```properties
   logging.level.{FQCN} = {level}
   ```

   ex) logging.level.root = info  (전체 로깅레벨 INFO)

   ex) logging.level.com.jaejoon.dao = debug (특정 패키지 로깅레벨 DEBUG)

   

2. **로그 파일 출력 설정**

   | `logging.file.name` | `logging.file.path` | 예         | 기술                                                         |
   | :------------------ | :------------------ | :--------- | :----------------------------------------------------------- |
   | *(없음)*            | *(없음)*            |            | 콘솔 전용 로깅.                                              |
   | 특정 파일           | *(없음)*            | `my.log`   | 지정된 로그 파일에 기록합니다. 이름은 정확한 위치이거나 현재 디렉토리에 상대적 일 수 있습니다. |
   | *(없음)*            | 특정 디렉토리       | `/var/log` | `spring.log`지정된 디렉토리에 씁니다 . 이름은 정확한 위치이거나 현재 디렉토리에 상대적 일 수 있습니다. |

   

3. **로그 파일 회전 설정.**

   | 이름                                                   | 기술                                                         |
   | :----------------------------------------------------- | :----------------------------------------------------------- |
   | `logging.logback.rollingpolicy.file-name-pattern`      | 로그 저장소 를 만드는 데 사용되는 파일 이름 패턴입니다.      |
   | `logging.logback.rollingpolicy.clean-history-on-start` | 애플리케이션이 시작될 때 로그 아카이브 정리가 발생해야하는 경우. |
   | `logging.logback.rollingpolicy.max-file-size`          | 보관되기 전 로그 파일의 최대 크기입니다.                     |
   | `logging.logback.rollingpolicy.total-size-cap`         | 로그 아카이브를 삭제할 수있는 최대 크기입니다.               |
   | `logging.logback.rollingpolicy.max-history`            | 로그 아카이브 보관 일수 (기본값 : 7)                         |



위의 설정을 응용하면 이런식으로 구성할 수있다.

```properties
local_path =C:/Users/dkans/Desktop/logs/
logging.level.root=debug
logging.file.name=${local_path}myapp.log
logging.logback.rollingpolicy.max-history=1000
logging.logback.rollingpolicy.max-file-size=4KB
logging.logback.rollingpolicy.file-name-pattern=${logging.file.name}.%d{yyyy-MM-dd}.%i.text
```



로그 결과물

![image](https://user-images.githubusercontent.com/64793712/104754518-a930e280-579c-11eb-8665-31ed592f0ae5.png)





## **커스텀 로그 설정파일 사용하기**



Spring-boot는 classpath 에 있는 logback-spring.xml 을 참조한다.

즉 resources 디렉토리 아래에  logback-spring.xml 파일을 생성해주면 된다.

![image](https://user-images.githubusercontent.com/64793712/104755363-ac789e00-579d-11eb-9f4f-249014a08780.png)



**주의)** application.properties  파일에 있는 log 설정이 먼저 설정하고 다시 logback-spring.xml이 설정되기 때문에 

application.properties 파일에 log설정은 적용되지 않습니다.



따라서 logback-spring.xml 에 다음과 같이 설정하면  프로퍼티들을 가져다가 사용할 수있음.

![image](https://user-images.githubusercontent.com/64793712/104756819-a08ddb80-579f-11eb-9573-f9df7282333f.png)





profile 별로 사용할 로그 프로퍼티 파일을 정의 후

![image](https://user-images.githubusercontent.com/64793712/104757005-db900f00-579f-11eb-8096-00a1b046f259.png)





logback-spring.xml 에서 다음과같이 작성해보자.

```xml
<springProfile name="{profileName}">
    <property resource="{적용할 profile name}"/>
</springProfile>

<property name ="{사용할 변수명}" value="${적용할 profile 에 들어있는 key 값}"/>
```



이런식으로 작성하면 프로퍼티들을 가져다가  적용 할 수 있다.


