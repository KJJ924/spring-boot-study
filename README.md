# spring-boot-study

[ Inflearn ] 스프링 부트 개념과 활용 강의를 듣고 챕터별 간단하게 정리합니다.

내용적 오류가 존재 할 수 있으며 부족한 부분은 언제든지 지적해주시면 감사드립니다.

------



# 1. 의존성 관리 이해

* 1. 스프링부트는 어떻게 해서 수 많은 의존성을 자동으로 가져올까?

![image](https://user-images.githubusercontent.com/64793712/104025027-5f2d8700-5207-11eb-810f-a99022c17716.png)  
정의한 의존성은 다음과 같이 2가지만 정의 되어 있고 의존성을 정의할때 버전도 쓰지 않는다.   



그 이유는 다음과 같다.



Spring boot  는 `parent 폼`에  스프링 boot 에서 사용할 의존성들이 이미 모든 버전이 서로 호환성이 맞도록 명시가 되어 있다.  
![image](https://user-images.githubusercontent.com/64793712/104026532-68b7ee80-5209-11eb-92ab-8d1702703302.png)  
*<spring boot 2.4.1> 버전에 맞는 의존성을 이미 명시가 되어있음*

이러한 의존성 관리를 자동으로 해줌으로써 개발자가 관리해야하는 의존성이 줄어들게된다.

------




# 2. 의존성 관리 응용

## 의존성 추가방법  

  + 1-1 스프링 부트 parent 에서 **관리하는** dependency 추가시.    
    ![image](https://user-images.githubusercontent.com/64793712/104026964-0e6b5d80-520a-11eb-9647-a4b65a498952.png)  
      *다음과 같이 원하는 dependency 를 추가하면된다. starter 같은경우   
      당연히 parent 폼에서 관리하는 의존성이기 때문에 버전을 명시하지 않아도된다.*  


  + 1-2 스프링 부트 parent 에서 **관리하지 않는** dependency 추가시.   
    ![image](https://user-images.githubusercontent.com/64793712/104027410-b2550900-520a-11eb-9af6-1c164d8fe1de.png)  
      *스프링 부트에서 관리하지 않는 버전이기 때문에 반드시 버전을 명시해준다*
  + 1-3 스프링 부트에서 관리하는 의존성의 **버전을 변경하고 싶은 경우**.  
    ![image](https://user-images.githubusercontent.com/64793712/104028132-b3d30100-520b-11eb-949e-9c056ae17abc.png)  
      *properties 내에 변경하고 싶은 버전을 명시해주면 된다.*

------



# 3. 자동설정의 이해

Spring boot 을 실행 할때 우리는 `@SpringBootApplication` 애노테이션이 붙은 클래스를 찾아가서 실행을한다.  
![image](https://user-images.githubusercontent.com/64793712/104031200-c6e7d000-520f-11eb-8993-202a0dc5f514.png)  
`@SpringBootApplication` 이라는 애노테이션 만으로 webApplication 을 구동 시킬수있다.*  

어떻게 `@SpringBootApplication` 이라는 애노테이션만으로 많은 자동설정을 할 수 있을까?  



`@SpringBootApplication`  애노테이션을 알아보자

![image](https://user-images.githubusercontent.com/64793712/104036850-44fba500-5217-11eb-92df-63cb7f774be2.png)


여기서 핵심은 

```
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
```

이 3가지의 애노테이션이다.



1. `@SpringBootConfiguration`

   -> `@Configuration`  이랑 동일하다 `@Configuration` 은 빈을 등록하기위한 설정 애노테이션이다 spring 기초에서 많이 보았다. 

   

   

2. `@ComponentScan`

   -> `@Component` 가 달린 애노테이션을 찾아 빈으로 등록해주는 애노테이션이다.

   

   

3. `@EnableAutoConfiguration`

   -> 여태까지 springboot 를 사용하고 처음보는 애노테이션이다. 더  바로 아래에서 자세히알아보자.

   

#### **@EnableAutoConfiguration**



`@EnableAutoConfiguration` 애노테이션은 

![image](https://user-images.githubusercontent.com/64793712/104035645-bf2b2a00-5215-11eb-8bf6-304ba2e46d70.png)


 java -> resource 디렉토리 ->  META-INF-> spring.factories 파일에 있는

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration
```

키값에 존재하는 value 들을 자동으로 설정해준다.


![image](https://user-images.githubusercontent.com/64793712/104035391-6cea0900-5215-11eb-87f9-dcb5a5196fb8.png)



어? 그러면은 `@EnableAutoConfiguration` , `@ComponentScan` 둘 중 누가 먼저 실행되어 빈을 등록 할까?

1단계: @ComponentScan

2단계: @EnableAutoConfiguratio

순으로 실행되어 빈을 등록한다.           **이 부분은 중요하니 꼭 기억하자 !**  


------

## 4. 자동 설정 만들기 1부: Starter와 AutoConfigure



1.  **이 챕터에서 하고자하는 것**

   1-1.  spring-boot 가 실행될때 AutoConfigure를 이용하여 외부 프로젝트에서 설정한 Bean을 자동으로 주입받아 사용하는 것이 주된 목표이다.



2. 시작

   2-1. Starter 와 AutoConfigure 의 네이밍 패턴은 다음과 같다.

   | Naming patterns               | 의미               |
   | ----------------------------- | ------------------ |
   | Xxx-spring-boot-autoconfigure | 자동 설정          |
   | Xxx-spring-boot-starter       | 필요한 의존성 정의 |



그냥 하나로 만들고 싶을 때는  Xxx-spring-boot-starter  Naming pattern 을 따르면 된다.

![image](https://user-images.githubusercontent.com/64793712/104123682-00474980-5390-11eb-81a9-9e19803d7efe.png)



해당 프로젝트에 다음과 같은 의존성을 추가한다.

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-autoconfigure</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-autoconfigure-processor</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.4.1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```



이제 Bean으로 등록할 class를 하나 만들어보자.

```java
public class JaeJoon {

    private String name;
    private Integer age;
    
   //Gatter , Setter , 생성자 , 생략 
   
 }
```



이 Class 를 Bean으로 등록하기 위해 자바설정 파일을 생성해야 한다.

```java
@Configuration
public class JaeJoonConfig {

    @Bean
    JaeJoon jaeJoon(){
        return new JaeJoon("김재준",26);
    }
}
```



이제 부터 핵심인 `@EnableAutoConfiguration` 이  해당 프로젝트 자동 설정을 찾을 수 있도록 셋팅을 해보자.



 **java -> resource 디렉토리 ->  META-INF-> spring.factories 생성**

![image](https://user-images.githubusercontent.com/64793712/104124610-199ec480-5395-11eb-9c3e-1169b3968afc.png)



- **spring.factories**  
  - **key : org.springframework.boot.autoconfigure.EnableAutoConfiguration**  
  - **value: FQCN(Fully Qualified Class Name)**

![image](https://user-images.githubusercontent.com/64793712/104124867-8c5c6f80-5396-11eb-8647-59d47fa8d394.png)




위 같은 셋팅을 다 끝낸 후 mvn intall 실행하여 빌드하면 된다.

![image](https://user-images.githubusercontent.com/64793712/104124764-df81f280-5395-11eb-9bd7-bb43ddcf2127.png)







빌드가 다 끝났다면 다시 원래의 spring-boot 프로젝트의 pom.xml 에 내가 빌드한 프로젝트를 주입하면 끝난다.



```xml
<dependency>
    <groupId>org.example</groupId>
    <artifactId>jaejoon-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```



이제 사용해보자 !

```java
@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Autowired
    JaeJoon jaeJoon;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(jaeJoon.getName());
        System.out.println(jaeJoon.getAge());
    }
}
```



해당 Spring-boot 프로젝트에선 JaeJoon을  bean으로 등록한 적 없지만 주입 받아서 사용 할 수 있다. 결과는 다음과 같다.

![image](https://user-images.githubusercontent.com/64793712/104124849-646d0c00-5396-11eb-9d94-1fc278208145.png)




------

## 5. 자동 설정 만들기 2부



그럼 여기서 하나의 의문이 생긴다.



AutoConfiguration 으로 등록되는 빈은 수정이 되지 않는가?  -> 일단은 그렇지 않다.



얼마든지 사용자가 커스텀을 통해 객체를 수정 할 수 있다. 다만 몇 가지의 설정이 필요하다.



일단 Bean을 오버라이딩 하여  기본 프로젝트에서 재정의 해보자.

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }

    //  재준을 다시 재정의 해보자.
    @Bean
    JaeJoon jaeJoon (){
        return new JaeJoon("김재준",27);
    }

}
```



나의 예상대로면 26살이던 재준이는 27살로 print 되어야 한다. 



우리의 예상대로 흘러가지 않는다.



스프링 부트 2.1 이상부터는 다음과 같은 오류를 만나 볼 수 있다.

![image](https://user-images.githubusercontent.com/64793712/104128914-8aea7180-53ad-11eb-8ed4-c870d36fd4d3.png)

오류를 읽어보자면 빈의 재정의를 금지 하고있다.  



해결 하기위해 아래의 Action 이 시키는 대로 해보자.



application.properties 파일에   다음과 같이 추가해보자 !

```properties
spring.main.allow-bean-definition-overriding=true
```

![image](https://user-images.githubusercontent.com/64793712/104129070-54f9bd00-53ae-11eb-8b5d-c0efe4ce1c19.png)



그럼 시키는대로 했으니 과연 나이가 27살로 변경 되었을까? 다시 실행해보자.

![image](https://user-images.githubusercontent.com/64793712/104129123-8ecac380-53ae-11eb-954b-68cb0a34111b.png)



어림도 없다. 여전히 26살로 나온다.



왜 아직도 재준이는 26살 일까?



이 의문에 대한 답은 위에서 정리한 내용에서 찾을 수 있다.



#### **3.자동설정의 이해 부분** 을 다시 보자



*어? 그러면은 `@EnableAutoConfiguration` , `@ComponentScan` 둘 중 누가 먼저 실행되어 빈을 등록 할까?*

*1단계: @ComponentScan*

*2단계: @EnableAutoConfiguratio*

*순으로 실행되어 빈을 등록한다.           **이 부분은 중요하니 꼭 기억하자 !***  



같이 설명 하고 있다.



내가 정의 새로 정의한  다음과 같은 코드는 @ComponentScan 에서 이미 등록이 끝난다.

```java
  //  재준을 다시 재정의 해보자.
    @Bean
    JaeJoon jaeJoon (){
        return new JaeJoon("김재준",27);
    }
```



하지만 @EnableAutoConfiguratio이 빈을 등록하는 차례에서 다시  재준이는 26살로 다시 돌아간다.



이제 이러한 문제을 다시 해결해보자



- #### **`@ConditionalOnMissingBean` 애노테이션 을 통한 해결.**

영어 뜻 그대로 해당 Bean 이 존재하지 않을때만 26살 재준이를 등록한다는 것이다. 



다음과 같이 JaeJoon.spring.boot.starter  프로젝트 로 돌아와서 셋팅해주면된다.

```java
@Configuration
public class JaeJoonConfig {

    @Bean
    @ConditionalOnMissingBean  // 이거 추가해주면 OK ! !
    JaeJoon jaeJoon(){
        return new JaeJoon("김재준",26);
    }
}

```



그럼 다시 springboot 를 실행시키면 27살 재준이를 만날 수 있다.

![image](https://user-images.githubusercontent.com/64793712/104129670-65129c00-53b0-11eb-9465-db95310921e6.png)





다 좋은데 너무 불편하다.

JaeJoon 의 나이만 변경할려고  JaeJoon 의 Bean 일일이 빈설정을 통해서만 변경이 가능한건가?  -> 아니다  방법이 있다.



- #### **properties 을 통한 초기값 세팅**

  다음과 같이 springboot 프로젝트에 있는 **`application.properties`** 를 이용하여 값을 쉽게 변경해보자.

  ```properties
  jaejoon.age=27
  jaejoon.name=김재준
  ```

  위 행동처럼 작동하기 위해 JaeJoon.spring.boot.starter 에서 다시 셋팅을 해보자.

  

  일단 properties 을 받을 수 있는 Class 하나를 정의 해보자.

  ```java
  @ConfigurationProperties("jaejoon") // 
  public class JaeJoonProperties {
  
      private String name;
      private int age;
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public int getAge() {
          return age;
      }
  
      public void setAge(int age) {
          this.age = age;
      }
  }
  ```

  

  **@ConfigurationProperties(prefix string)**

  ```properties
  jaejoon.age=27
  jaejoon.name=김재준 
  ```

  에서 prefix를 정의 해줘야하기때문에 jaejoon 이라고 값을 준 것 이다.

  

  이제 JaeJoonConfig 로와서 적용해보자.

  ```java
  @Configuration
  @EnableConfigurationProperties(JaeJoonProperties.class) // JaeJoonProperties.class 를 사용하겠다.
  public class JaeJoonConfig {
  
      @Bean
      @ConditionalOnMissingBean // 빈이 없을때 application.properties 에 있는 값을 보고 Bean 을 생성하게 됨.
      JaeJoon jaeJoon(JaeJoonProperties jaeJoonProperties){
          JaeJoon jaeJoon = new JaeJoon();
          jaeJoon.setName(jaeJoonProperties.getName());
          jaeJoon.setAge(jaeJoonProperties.getAge());
          return jaeJoon;
      }
  }
  ```



이제 Bean 을 재정의 하지 않아도 **`application.properties`** 통해 얼마든지 Bean의 값을 변경 할 수 있다.

| 설정                                                         | 결과                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://user-images.githubusercontent.com/64793712/104130361-93de4180-53b3-11eb-8e64-3e3d4687c4d5.png) | ![image](https://user-images.githubusercontent.com/64793712/104130372-a35d8a80-53b3-11eb-9a90-5ee90becd1b8.png) |

------

# **SpringApplication**



## **ApplicationEvent 등록**



Spring core 부분을 공부하면서 ApplicationContext  Interface 가 상속 받은 Interface 중에 ApplicationEventPublisher 를 상속 받고 있다는 것을 알고 있다.

<img src="https://user-images.githubusercontent.com/64793712/104308256-b7200280-5513-11eb-9f52-2de1ccbb6525.png" alt="image" style="zoom:150%;" />



#### 그러면 **ApplicationEventPublisher** 의 역할은 무엇을 하는 것일까 ?



ApplicationEventPublisher 은  옵저버 패턴의 구현체로 이벤트 프로그래밍에 필요한 기능을 제공한다.



예를 들어보자.

```java
public class Member {

    String name;

    public Member(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

```

간단한 Member 클래스이다.



우리는 이 Member 객체가 생성 될때마다 해당 Member 에게 인사를 해주고 싶다.



그러면 다음과 같은 설정을 진행 해야 한다.



1. ApplicationEvent.Class 를 상속 받는다.

```java
public class Member extends ApplicationEvent {

    String name;
    
    public Member(Object source, String name) {
        super(source);
        this.name = name;
    }
    //getter, setter 생략
}
```



2. Event를 처리할 Listener를 만든다. (해당 Listener 는 Bean으로 등록 되어 있어야 함.)

```java
@Component
public class SampleListener implements ApplicationListener<Member> {
    
    @Override
    public void onApplicationEvent(Member member) {
        System.out.println("반가워요 "+member.getName());
    }
}
```

3. 마지막으로 이벤트를 발생 시켜 주면 된다.

   ```java
   @Component
   public class ApplicationRunner implements ApplicationRunner {
       
       @Autowired
       ApplicationEventPublisher applicationEventPublisher;
   
       @Override
       public void run(ApplicationArguments args) throws Exception {
           Member member = new Member(this,"김재준");
           applicationEventPublisher.publishEvent(member);
       }
   }
   ```



이렇게 Member 에 대한 Event를 생성 후 event를 발생 시킬 수 있다.



하지만 Member.Class 가 ApplicationEvent 를 상속 받음으로써 Spring 와 강한 의존성이 생겼다.



이러한 문제점을 해결 하기 위해 Spring 4.3 이후로 부터는 POJO 스럽게 Event 를 등록 할 수 있다.



 먼저 Member.Class 에 있는 상속 구조부터 제거하자

```java
public class Member{
    String name;

    public Member(String name) {
        this.name = name;
    }
	//getter, setter 생략
}
```



또한 Listener 도  **ApplicationListener<E>**  대신 **`@EventListener`** 를 이용하여 대체가 가능하다.

```java
@Component
public class SampleListener  {

    @EventListener
    public void MemberCreateEvent(Member member) {
        System.out.println("반가워요 "+member.getName());
    }
}
```



이제 똑같이 실행 해주면 아까의 코드와 동일하게 작동한다.

```java
@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member member = new Member("김재준");
        applicationEventPublisher.publishEvent(member);
    }
}
```



앞서 말한 Event 등록은 ApplicationContext 가 생성되고 나서 작동하는 Event 들 이다.



ApllicationContext 가 등록되기 이전의 Event 를 발생 하고 싶으면  다음과 같은 설정을 해주면 된다.



```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        springApplication.addListeners(new SampleListener()); // Listeners를 등록 한다.
        springApplication.run(args);
    }
}
```



.addListeners() 이 ApplicationListener<?>  타입을 파라미터를 받기 때문에 앞서 말한 **`@EventListener`** 를 사용하지 못한다.

```java
@Component
public class SampleListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        System.out.println("ApplicationContext 가 생성하기전에 출력해 ");
    }
}
```

또한 ApplicationListener  interface 는  FunctionalInterface 임으로 람다식으로  간단히 표현 할 수있다.

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        springApplication.addListeners((ApplicationListener<ApplicationStartingEvent>) applicationEvent -> {
            System.out.println("이렇게 할수도 있어요");
        });
        springApplication.run(args);
    }
}
```





결과

![image](https://user-images.githubusercontent.com/64793712/104311929-3ebc4000-5519-11eb-9e68-c800a7d01815.png)


---


## **외부설정**(application.properties)



이번 챕터에서는 **`application.properties`** 의 우선순위와 사용방법을 알아 볼 것이다.



**프로퍼티(properties) 의 정의는 단순한 Key 와 Value 로 형태로 제공하는 Data이다.**



**프로퍼티 우선 순위**

1. [spring-boot-devtools](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-devtools)를 활성화 시켰을 때 `$HOME/.config/spring-boot` 디렉토리에 안에서 제공하는 프로퍼티
2. 테스트에 사용한 `@TestPropertySource`가 제공하는 프로퍼티
3. `@SpringBootTest` 또는 슬라이스 테스트용 애노테이션의 `properties` 속성으로 제공하는 프로퍼티
4. 커맨드 라인 아규먼트
5. `SPRING_APPLICATION_JSON` 환경 변수 또는 시스템 프로퍼티에 인라인 JSON으로 정의되어 있는 프로퍼티
6. ServletConfig 초기 매개변수
7. ServletContext 초기 매개변수
8. java:comp/env에 들어있는 JNDI 애트리뷰트
9. 자바 시스템 프로퍼티 (System.getProperties())
10. 운영체제 환경 변수
11. `RandomValuePropertySource`. `random` 접두어를 가지고 있는 프로퍼티, `random.*` 에 무작위 값을 제공하는 프로퍼티 소스.
12. JAR 패키지 외부에 있는 특정 프로파일용 애플리케이션 프로퍼티. (application-{profile}.properties 또는 YAML
13. JAR 패키지 내부에 있는 특정 프로파일용 애플리케이션 프로퍼티. (application-{profile}.properties 또는 YAML
14. JAR 패키지 외부에 있는 애플리케이션 프로퍼티. (application.properteis 또는 YAML)
15. JAR 패키지 내부에 있는 애플리케이션 프로퍼티. (application.properteis 또는 YAML)
16. `@Configuration` 클래스에 사용한 `@PropertySource`로 읽어들인 프로퍼티
17. SpringApplication.setDefaultProperties()로 설정할 수 있는 기본 프로퍼티

*(출처 : https://www.whiteship.me/spring-boot-external-config/)*



자 그럼 다음과 같이 spring-boot 프로젝트를 생성했을때 resources 밑에 있는 apllication.properites 의 우선순위는 몇 위 인가?

![image](https://user-images.githubusercontent.com/64793712/104452489-6c22ef80-55e6-11eb-8478-ca15416a6299.png)

JAR 패키지 내부에 있는 애플리케이션 프로퍼티. (application.properteis 또는 YAML)  임으로 15위가 된다.



### **사용방법**

자 그러면  `application.properties` 안에   **키(key)와 값(value)** 를 등록하고 또 사용해보자.

```properties
test.projectName = testProject
test.version = ${random.int}
test.projectNameAndVersion=${test.projectName} ${test.version}
```

application.properties 에 다음과 같이 작성 해보자.



${random.~~} 을 통하여 Random 한 인수 값을  Setting 할 수 있고



Property Placeholders 를 이용하여 미리 정의 된  Value 값들을 이용할 수 도 있다.

   

간단한 외부설정이 끝났으니 외부설정을 사용해보자.

```java
@Value("${test.projectName}")
String projectName;

@Value("${test.version}")
int version;

@Value("${test.projectNameAndVersion}")
String projectNameAndVersion;
```

다음과 같이 `@Value`  애노테이션을 이용하여 외부 프로퍼티 에 작성되어 있는 값을 참조하여 해당 변수에 할당 할 수 있다.





**Test 코드 를 작성하면서 발생 할 수 있는 상황을 알아보자**



만약 Test 코드를 작성한다고 하였을때 실제 서비스에 있는 `application.properties` 의 외부설정을 사용하고 싶지 않고

Test 만의 `application.properties` 을 따로 두고 싶을땐 어떻게 해야 할까?



```java
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    Environment environment;
    @Test
    void propertiesTest() {
        Assertions.assertThat(environment.getProperty("test.projectName")).isEqualTo("testCodeProject");
    }

}
```

이 코드 처럼 이제 test.projectName(key) 값이 testCodeProject(value) 값을 가지고 싶다.



하지만 classPath:/ 밑에 있는 `application.properties` 의 파일은 지금 하나 이다. 따라서 위에 있는 Test 는 실패 할 것이다.



따라서 Test classPath:/ 아래에 똑같이 `application.properties` 를 정의하여 사용해보자.

![image](https://user-images.githubusercontent.com/64793712/104455463-a7bfb880-55ea-11eb-8da4-0c1681255e42.png)

 다음  `application.properties`  에  test.projectName(key) 에대한 value 값을 재 정의 해보자.

```properties
test.projectName = testCodeProject
```



그 다음  Test 를 실행하면?



![image](https://user-images.githubusercontent.com/64793712/104456006-6a0f5f80-55eb-11eb-919c-3cddfd128972.png)

오류가 발생 할 것이다.



왜냐하면 test 밑에 있는 application.properties 가  원래 존재하고 있는 application.properties 를 오버라이딩 하기 때문에 

```java
@Value("${test.version}")
int version;
```

이 코드에서 test.version 을 값을 찾을 수 없기때문이다. 



이러한 문제점을 해결하기 위해선 test 밑에 있는  `applcation.properties` 랑 기존 사용하고 있는  `application.properties` 의  프로퍼티 값을 일치 해주면 되는데.



매우 귀찮다.

우리는 test.projectName 의 값만 변경하고싶은데 나머지 프로퍼티 값도 작성하는 것은 비효율 적이다.



1. **`@TestPropertySource(properties = "")`**  를 이용하는 방법  배열로 여러가지의 프로퍼티 값을 재정의 할수 있습니다.

   ![image](https://user-images.githubusercontent.com/64793712/104457970-0fc3ce00-55ee-11eb-8919-f799fcbb8cfa.png)

test 밑에있는 `application.properties` 을 삭제하고 `@TestPropertySource` 이용하여 해결할 수 있다.



2. **@TestPropertySource(locations = "")** 배열로 여러개의 프로퍼티 값을 바꾸기 힘들 때  파일을 이용하여 재정의 할 수 도 있다.

![image](https://user-images.githubusercontent.com/64793712/104457585-92985900-55ed-11eb-94a9-04b0b4ae24df.png)


---

## **외부설정 2부**



앞에서 살펴봤던 외부프로퍼티에 대한 바인딩은 전혀 **Type safe** 하지 않다.

```java
@Value("${test.projectName}")
String projectName;

@Value("${test.version}")
int version;

@Value("${test.projectNameAndVersion}")
String projectNameAndVersion;
```



@Value("${~~}") 안에 적는 외부 properties 의 key 값이 정확히 일치 해야 해당 변수에 바인딩 된다.



이러한 경우 대신 자바 bean 스펙을 이용하여 객체에 직접 바인딩을 해줄 수 있다.

![image](https://user-images.githubusercontent.com/64793712/104595029-57615d00-56b5-11eb-934a-1949f183020f.png)

해당 prefix 값을 기억하자.



그리고 바인딩 받을 수 있는 class 를 하나 만들자. **(프로퍼티 를 주입받기 위해선 Java Bean 스펙 준수)**

```java
public class Test {
    private String projectName;
    private int version;
    private String projectNameAndVersion;
    
    //getter ,setter 추가
}
```

class 이름은 마음대로 정해도 된다. 



그리고 해당 class 위에 `@ConfigurationProperties` 애노테이션을 선언한다.

![image](https://user-images.githubusercontent.com/64793712/104600271-20db1080-56bc-11eb-9f9f-03cf8abaeef2.png)

그리고 아까 기억하고있는 prefix 값을 적어준다.

그리고 해당 class 를 @Component 를 이용하여 Bean으로 등록해주면 끝이다.



전체코드

```java
@Component
@ConfigurationProperties("test")
public class Test {

    private String projectName;
    private int version;
    private String projectNameAndVersion;

   //getter ,setter 추가
}
```

|                              전                              |                              후                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/64793712/104598562-10c23180-56ba-11eb-86d9-cfd8733d6162.png" alt="image" style="zoom:80%;" /> | ![image](https://user-images.githubusercontent.com/64793712/104599090-b5dd0a00-56ba-11eb-8f6f-90027f17c8b6.png) |





또한 바인딩시 값에대한 검증도 같이 할 수 있다.

 `@Validated` 애노테이션을 이용한 JSR-303 (@NotNull, ...)   검증 이 가능하다.



**spring-boot 2.3 이후 부터는 의존성을 추가해줘야 합니다.**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

---

## **프로파일**(@Profile)



Spring 사용시 환경에 맞는 설정 파일을 @Profile() 애노테이션을 통해 Product  환경과 Test 환경에서 사용할

Config 파일들을 구분 할 수 있었다.



Spring-boot 에서도 마찬같이로 똑같이 기능을 사용할 수 있다.



```java
@Profile("test")
@Configuration
public class TestConfig {

    @Bean
    String hello(){
        return "TestConfig";
    }
}
```



```java
@Profile("product")
@Configuration
public class ProductConfig {

    @Bean
    String hello(){
        return "ProductConfig";
    }
}
```



위와 같이 2개의 Config 파일이 존재 할때  실행환경마다 해당하는 Profile을 사용하고 싶다면.



```properties
spring.profiles.active=product
```



다음과 같이 application.properties 에  `spring.profiles.active`  key 값을  작성하면 된다.



또한 `application.properties` 도 운영환경마다 사용할 설정파일을 지정할수 있는데 다음과 같은 패턴으로 작성하면된다.

 `application-{profile}.properties`



이렇게 선언후

![image](https://user-images.githubusercontent.com/64793712/104732008-e2a72500-577f-11eb-84b9-c34e43704de1.png) 

해당 부분에 어떤 profiles 를 사용할지 적어주면된다.

---

## Logging



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

1.  STDOUT 의 ConsoleAppender 임으로 log를  콘솔창에 출력

2.  STDOUT 의 로그 메세지의 출력 포맷은  %d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n 다음과 같음.

3.  chapters.configuration 패키지 의 로그를 INFO 레벨로 출력.

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





**커스텀 로그 설정파일 사용하기**



loSpring-boot는 classpath 에 있는 logback-spring.xml 을 참조한다.

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



logback.xml 설정은 위에서 설명했으니 생략.



---
## **Test**



우리는 Spring-boot 를 사용하면 대부분 WebApplication 을 개발 할 것이다.

WebApplication을 개발 함에 있어 또한 대부분 MVC 패턴을 이용하여 개발을 진행한다.

또한 개발을 진행함에 있어 Test 코드의 작성은 선택이 아닌 필수이다.



Spring-boot는 Test를 작성함에있어 그렇게 어렵지 않으니 한번 알아보자.



자 다음과 같은 SampleContoller와 SampleService 가 있다.



```java
@RestController
public class SampleController {

    @Autowired
    SampleService sampleService;

    @GetMapping("hello")
    public String helloName(){
        return sampleService.getName()+"hello";
    }
}
```



```java
@Service
public class SampleService {

    public String getName(){
        return "jaeJoon";
    }
}
```



SampleController 는 "/hello" 이라는 요청이 들어오면 SampleService 의 getName() 에서 jaeJoon 을 받아

RestController 임으로 응답의 Body 부분에 jaeJoonhello 를 리턴 해 줄 것이다.



이러한 흐름을 Test 코드를 통해 검증 하고 싶을때 다음과 같이 진행하면 된다.



1. **`@SpringBootTest`**

   -> 1-1)   webApplication 통합테스트를 진행한다.(default: 내장 톰캣을 사용하지 않고) 

   ![image](https://user-images.githubusercontent.com/64793712/104846435-d953bf00-591d-11eb-8786-5e12f71ba3bb.png)

   ​																위와 같이 Mock up된 웹환경을 조작하기 위해선 **MockMvc** 를 통한 조작을 해야함

   -> 1-2)  Junit4 사용시 @RunWith(SpringRunner.class) 와 함께 사용해야함( Junit5 사용시 적용하지 않아도 됨)

2. **`@AutoConfigureMockMvc`**

   -> 위에서 말한 **MockMvc** 를 주입받기위한 애노테이션



자 이제 활용해보자.

```java
@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Test
    void hello() throws Exception{
    	// 테스트코드 작성 !
    }
}
```



이제 MockMvc의 Method를 알아보자.

크게 다음 과 같이 3가지로 나눌 수 있다.

1. `perform()`

   ->  어떠한 요청을 실행 할 것인지 결정하는 Method (ex: get("url") , post("url") ,  put("url")  등등 )

2. `andDo()`

   -> 요청을 실행하면서 할 행동

3. `andExpect()`

   -> 요청의 기대값 비교

대충 이렇게 설명 할 수 있는데 3가지 Method를 체이닝하여 이용한다.



이제 이용해보면..

```java
@Test
    void hello() throws Exception{
        mockMvc.perform(get("/hello"))
                .andDo(print())
            	.andExpect(status().isOk())
                .andExpect(content().string("jaeJoonhello"));
    }
}
```

다음과 같이 작성할수 있는데..



 perform 안에있는 get() 이라는 Method는  org.springframework.test.web.servlet.request.MockMvcRequestBuilders  에서 온다.

말그대로 get 요청으로 /hello 를 호출한다고 보면된다.

또한 print() ,status() .content 들은

org.springframework.test.web.servlet.result.MockMvcResultMatchers 에서 온 녀석들임으로 결과의 Response 값들을 출력하거나 확인할수있는 Method 들 이라고 생각하자.





지금은 ApplicationContext 안에 들어 있는 Bean들을 그대로 이용하여  Test 코드를 작성했다.

하지만 때때로 ApplicationContext  안에 있는 Bean들을 그대로 이용해서 Test 코드를 작성하기에 힘든경우가 발생할 수 있다.

그러한 경우 ApplicationContext 안에 있는 Bean을 Mock Up 하여 Test 코드 환경에서 필요한 Bean을 주입 할 수 있다. 다음과 같이 작성하면된다.



```java
@MockBean
SampleService sampleService;
```

일단 Mock up 할  클래스를 @MockBean 애노테이션을 붙여 선언 후

```java
@Test
void hello() throws Exception{
    when(sampleService.getName()).thenReturn("mock Name"); // Test에서 사용할 getName() 의 리턴 값을 결정!
    mockMvc.perform(get("/hello"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("mock Namehello"));
}
```

다음과 같이 /hello 라는 요청이 들어와서 SampleService 에있는 getName을 호출하게되면 Test 코드에서 임시로 만든 Mock SampleService  주입하여 Test코드를 유연하게 짤수있다.



이러한 기능은 통합테스트 환경이아닌 단위테스트 환경에서 더욱 큰힘을 발휘한다.



앞서 말한 통합테스트 환경이아닌 단위테스트 환경으로 변경하기위해선 `@SpringBootTest` 가 아닌 `@WebMvcTest` 를 사용해보자.

```java
@WebMvcTest(SampleController.class)
@AutoConfigureMockMvc
class MockSampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void hello() throws Exception{
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("mock Namehello"));
    }
}
```

다음과 같이 `@WebMvcTest(SampleController.class)`   즉 SampleController 만  테스트를 진행하고 싶을때 

이 상태로 실행하게 된다면 다음과 같은 오류를 만나보게 될 것이다.

![image](https://user-images.githubusercontent.com/64793712/104847299-96e0b100-5922-11eb-8c22-5227fbba0c57.png)

SampleService 의 Bean을 찾지 못하여 생기는 오류인데 당연히 통합테스트가아닌  단위테스트 임으로 SampleService 빈은 등록되지 않는다.

이러한 경우 앞서 살펴보았던 MockBean 을이용하여 가짜Bean을 등록하여 테스트를 진행 할 수있다.



```java
@WebMvcTest(SampleController.class)
@AutoConfigureMockMvc
class MockSampleControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
   	SampleService sampleService;

    @Test
    void hello() throws Exception{
        when(sampleService.getName()).thenReturn("mock Name");
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("mock Namehello"));
    }
}
```



앞서말한 단위테스트말고도 spring boot 는 더 많은 단위테스트를 제공함으로 자세히 알고 싶다면 spring boot doc 에서 test 부분을 살펴보자.

https://docs.spring.io/spring-boot/docs/2.4.0/reference/html/appendix-test-auto-configuration.html#test-auto-configuration


---
## **Spring-boot  웹MVC 소개 , ViewResolver**



이제 spring-boot 가 자동설정 하는 Configuration 중  WebMvcAutoConfiguration 에 대해 서 알아보자.



WebMvcAutoConfiguration 은  SpringMvc 의 자동설정 Configuration 이며 Springboot 를 사용하면서 많은 자동설정들을 도와준다.



Spring-boot 를 실행하면서 자동 설정을 확장하거나 재정의 하는 방법은 다음과 같이 진행하면 된다.



1. #### **SpringMvc 확장**

   ```java
   @Configuration
   public class WebConfig implements WebMvcConfigurer {
       
   }
   ```

   하나의 설정파일에  WebMvcConfigurer 를 구현하면된다.

   여기서 WebMvcConfigurer 인터페이스가 제공하는 콜백 함수를 이용하여 사용자가 필요한 설정을 정의하면 된다.

   ![image](https://user-images.githubusercontent.com/64793712/104929102-d1ac1d00-59e6-11eb-9780-1e9aeb3bac7e.png)

   이와 같은  방법은 SpringMvc 의 자동설정을 유지하며 확장해서 기능을 추가 할 수 있기 때문에 이 방법을 추천한다.

   

   

   

2. #### **SpringMvc 재정의**

   ```java
   @Configuration
   @EnableWebMvc
   public class WebConfig implements WebMvcConfigurer {
   
   }
   ```

위 코드처럼 `@EnableWebMvc` 을 사용하게 된다면 Spring-boot 에서 제공해주는 자동설정은 사용하지않는다.

해당 방법은 스프링부트가 제공해주는 편리한 자동설정을 사용할 수 없음으로 권장하지 않는다.





### **ViewResolver** 

스프링 부트를 이용하면  다음과 같이 전략을 선택 하여 등록할 수 있는데

![image](https://user-images.githubusercontent.com/64793712/104933177-ed65f200-59eb-11eb-9fad-58f11472b892.png)



이 중에서 ContentNegotiatingViewResolver 를 살펴보자.



ContentNegotiatingViewResolver 은 요청 Headers 정보중  Accept 를 확인하여 원하는 Data를 내려줄 수 있다. 

![image](https://user-images.githubusercontent.com/64793712/104935839-163bb680-59ef-11eb-9d76-54136f965669.png)

[]: https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Accept	"Aceept 에 대한 링크"



Test 코드로 보면 다음과 같다.



```java
 @Test
    void getUser() throws Exception{
        User user =new User("JaeJoon",26);
        String content = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(equalTo("JaeJoon"))))
                .andExpect(jsonPath("$.age",is(equalTo(26))));
    }
}
```

받고 싶은 data format 을 accept 에 선언 한다면 해당 format 이 서버에 준비되어있다면 요청을 받을 수 있다.


---
## **정적리소스 지원**



spring boot 는 웹브라우저에서 요청이 들어 왔을때 이미 만들어져있는 리소스를 지원하는 기능이다.



spring boot 는 다음과 같은 위치에 있는  정적 리소스들을  제공해줄수 있다.

| 경로                              |
| --------------------------------- |
| **classpath:/public**             |
| **classpath:/resources**          |
| **classpath:/META-INF/resources** |
| **classpath:/static**             |



정적 리소스 맵핑 은 다음과 같으며 `/**`  해당 경로를 얼마든지 커스텀 할 수 있다.

-> apllication.properites 파일에 다음과 같이 설정하면된다.

`spring.mvc.static-path-pattern`

```properties
spring.mvc.static-path-pattern=/static/**
```



또한 요청에대한  정적리소스를 찾을 디렉토리도 변경이 가능한데 다음과 같다.

`spring.web.resources.static-locations`

```properties
spring.web.resources.static-locations=classpath:/static-location/
```



하지만 application.properites 를 통해  정적리소스의 locations 을 바꾸는 행위는 스프링 부트에 기본으로 등록되어있는 **정적리소스의 locations 들을 사용하지 못함으로 주의 해야한다.**



![image](https://user-images.githubusercontent.com/64793712/105033798-f0b8b680-5a9b-11eb-96d7-ff20bb0c73bf.png)

**위에 있는 값들을 오버라이딩 하는 행위임으로 !**





따라서 정적리소스의 위치를 추가하고 싶다면 다음과 같이 추가해보자.(1부 에서 소개 했던 방법을 이용하여)

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/m/**")
                .addResourceLocations("classpath:/m/")
                .setCachePeriod(30);
    }
}
```

해당 코드를 추가하게 된다면 

**http:/localhost:8080/m/~~** 요청이 들어온다면 

**~~** 에 해당하는 정적리소스 파일을 **classpath:/m/** 밑에있는 리소스 파일을 찾게된다.

| 디렉토리 구조                                                | 요청                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://user-images.githubusercontent.com/64793712/105034704-31fd9600-5a9d-11eb-8ec9-b8a72c7d9a74.png) | ![image](https://user-images.githubusercontent.com/64793712/105034749-42157580-5a9d-11eb-9b00-3958d1abf180.png) |



해당 방법으로 location 을 추가한다면 기본 전략에 내가 원하는 location 과 handler를 안전하게 추가할 수 있음으로 이방법을 권장한다.


---


## **Thymeleaf**



아마 WebApplication 을 한번이라도 개발 해봤다면 JSP 를 사용 했을 것이다.

thymeleaf 도  JSP와 같은 template 엔진이다.



둘의 차이점은 다름아닌 JSP는 서버를 통해 Servlet 코드로 변환되어 랜더링 하지만 thymeleaf 는

servelt 코드로 변환되지 않는다.



이 차이점으로 인해  thymeleaf 는 서버가 존재하지않아도 Html 을 확인 할 수 있지만 JSP 는 불가능 하다.

또한 JSP는 위에서말한 서버가 필수적이기 때문에 WAR 패키징을 진행해야하고 thymeleaf는 jar 패키징이 가능하다.



JAR 와 WAR 차이첨은 간단히 JRE 만 가지고 실행이 가능한 것과 아닌 것 으로 말 할 수 있다.



이제 Thymeleaf 을 간단히 사용해보자.



1. **pom.xml 의존성 추가하기**

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-thymeleaf</artifactId>
   </dependency>
   ```

   앞서 작성한 글에서도 언급한것 처럼  Thymeleaf  의 version도  parent 부분에 명시되어있음으로 생략이가능하다.

   ![image](https://user-images.githubusercontent.com/64793712/105344233-03f78d80-5c26-11eb-8da3-ef3875b2df31.png)



2. **html 파일에 namespace 추가하기**

   ```html
   <html lang="en" xmlns:th="http://www.thymeleaf.org">
   ```



3. **표현식 익히기**

   Jsp에도 표현식이 있는 것처럼 Thymeleaf  에도 해당하는 표현식이 존재한다. 다 살펴보지는 않고 기본적인것만 살펴보자.

   1. **${...} : 변수 표현식.**

      

      **html**

      ```html
      <!DOCTYPE html>
      <html lang="en" xmlns:th="http://www.thymeleaf.org">
      <head>
          <meta charset="UTF-8">
          <title>Title</title>
      </head>
      <body>
          <h1 th:text="${name}">name</h1> // controller 에서 담긴 Model 정보 참조
          <h1 th:text="${age}">age</h1>
      </body>
      </html>
      ```

      

      **Controller**

      ```java
      @GetMapping("th")
      public String testPage(Model model){
          model.addAttribute("name","JaeJoon");
          model.addAttribute("age",26);
          return "wellcome";
      }
      ```

      

   2. **#{...} : 메세지 표현식**

      설정파일(.properties) 에있는 값을 읽어와 값을 표시하는 표현식이다

      

      **html**

      ```html
      <!DOCTYPE html>
      <html lang="en" xmlns:th="http://www.thymeleaf.org">
      <head>
          <meta charset="UTF-8">
          <title>Title</title>
      </head>
      <body>
          <h1 th:text="#{jaejoon.name}"> properties value name</h1>
          <h1 th:text="#{jaejoon.age}">properties value age</h1>
      </body>
      </html>
      ```

      

      **application.properites**

      ```properties
      jaejoon.age=100
      jaejoon.name=JaeJoon
      ```

      

   3. **@{...} : 링크(URL) 표현식**

      ```html
      <!DOCTYPE html>
      <html lang="en" xmlns:th="http://www.thymeleaf.org">
      <head>
          <meta charset="UTF-8">
          <title>Title</title>
      </head>
      <body>
          <a href="#" th:href="@{'/testPage'}"> 페이지이동</a>
      </body>
      </html>
      ```

위에 작성한 HTML  실행을 해보면 JSP 처럼 깨져서 나오는게 아니라 대체 값이 나오는걸 확인 할 수 있다.

이처럼 Thymeleaf 는 서버에 의존하지않고 개발 할 수 있기때문에 개발이 편리하다.





위에서 설명한 3가지를  2가지가 더 남아있고 활용 방법은 아주 많다.

https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html

더 자세한 내용은 위 링크에서 통해 확인 할 수 있다.

---

## **ExceptionHandler**



REST API 개발시 개발자는 DB에 없는 값이나 URL에 대한 요청이 잘못되었을때 그에 맞는 ErrorMessage 를 넘겨줘야한다.

물론 REST API 개발이 아니여도 당연히 해야할 예외처리이다.



Spring-boot 에서는 요청에대한 Exception 이 발생했을때 그 요청을 받아 처리할 수 있는 Handler 를 제공해준다.



말로 설명보다 코드로 보는것이 이해하기 쉬우니  다음 예제코드를 보자.



### 1.**`@ExceptionHandler(Class<? extends Throwable>[] value() default {})`**

해당 애노테이션은  Controller 내부에서 발생하는 Exception만을 핸들링 할 수 있다.



```java
@Controller
public class SampleController {
    @GetMapping("page/{id}")
    public String page(@PathVariable int id){
        if(id==1) {
            throw new NotFoundPage("해당하는 페이지가 존재하지않음", id);
        }
        return "wellcome";
    }
}
```

여기 page/{id}  id 의 값대로 page를 보여주는 Get 요청이 존재한다.

예제 코드이니 ID 값이 1 일때 만  예외를 발생 시킬 것이다.



여기서 생성한 예외는 다음과 같다.

```java
public class NotFoundPage extends RuntimeException {

    private final Object value;

    public NotFoundPage(String message, Object value) {
        super(message);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
```



Contoller 에서 예외가 발생했으니 이제 예외를 컨트롤 할 수 있는 Handler 를 만들어보자.

```java
@Controller
public class SampleController {
    
    //@GetMapping("page/{id}") 코드 생략

    @ExceptionHandler(NotFoundPage.class)
    @ResponseBody
    public ResponseEntity<ApiMessage> notFoundException(NotFoundPage notFoundPage){
            ApiMessage apiMessage = new ApiMessage();
            apiMessage.setInputValue(notFoundPage.getValue());
            apiMessage.setMessage(notFoundPage.getMessage());
            apiMessage.setStatusCode("404");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiMessage);
    }

}
```



**@ExceptionHandler(NotFoundPage.class)**  이 한 줄이 핵심이다.



예외를 던질때 NotFoundPage 를 생성해서 던졌으니 이 예외를 여기서 처리한다는 뜻으로 이해하면된다.



예외가 발생할때 생성할 Message는 내부 규칙이 있다면 규칙에 따라 Message 를 담을 객체를 만들어 반환 하면된다.



이번 예제에서는 간단히  statusCode , Message , InputValue(사용자가 입력한 값) 만을  Message 로 보낸다.

```java
public class ApiMessage {

    private String statusCode;

    private String message;

    private Object InputValue;
    
    //getter ,setter 생략 
 }
```

 

**결과**

![image](https://user-images.githubusercontent.com/64793712/105469261-4d55e480-5cdb-11eb-9815-1495f776dbe1.png)





**@ExceptionHandler** 은 앞서 말한 것처럼   예제에 있는 SampleController 내부의  NotFoundPage 예외만 처리해준다 .

만약 다른 Controller 에서 NotFoundPage 예외가 발생해도 해당 예외는 SampleController 에있는 ExceptionHandler 에서 처리해주지 않는다.



모든 Controller 에 대해 전역적으로 ExceptionHandler 를 처리하기 위해선 다음과 같은 방법을 사용하면된다.





### **@ControllerAdvice**

해당 애노테이션은 모든 Controller 에서 발생하는 Exception을 받아 해당 Controller 에서 처리 할 수 있게 해준다.



코드를 바로 살펴보자.



```java
@ControllerAdvice  //  선언 후
public class ErrorController {

    @ExceptionHandler(NotFoundPage.class)  // ErrorController 에서 처리할 ExceptionHandler 를 작성 해주면 된다.
    @ResponseBody
    public ResponseEntity<ApiMessage> notFoundException(NotFoundPage notFoundPage){
        ApiMessage apiMessage = new ApiMessage();
        apiMessage.setInputValue(notFoundPage.getValue());
        apiMessage.setMessage(notFoundPage.getMessage());
        apiMessage.setStatusCode("404");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiMessage);
    }

}
```



아까 살펴본 SampleController 에있는  예외 처리 Handler를  해당  ErrorController.Class 로 옮김으로 써  좀 더  Controller 들의 책임을 명확하게 할 수 있다.

또한 이제 NotFoundPage 예외는  ErrorController 에서 처리함으로 중복코드도 제거된다.




---
# **CORS , SOP**



CORS 이슈는 개인프로젝트 REST API  애플리케이션 개발을 하면서 겪었던 이슈이다.

![image](https://user-images.githubusercontent.com/64793712/105623809-0e9d6700-5e60-11eb-8891-0612a5876e68.png)

이때는 CORS 가 뭔지도 몰랐다 ㅎㅎ.



일단 CORS 와 SOP 의 개념부터 알아보자.



## **SOP(Same Origin Policy)**



일단 Spring-boot 의 기본적으로 아무 것도 설정을 하지 않는다면 SOP 정책을 따른다.



그래서 SOP 가 무엇이냐면 

 

자바스크립트 엔진 표준 스펙의 보안 규칙으로

**같은 호스트, 같은 포트 , 같은 프로토콜** 에서만 접근이 가능한 정책(Policy) 이다.



그럼 SOP 에서 Origin은 (**같은 호스트, 같은 포트 , 같은 프로토콜**) 을 나타낸다고 보면된다.



이제 Same Origin Policy (동일 출처 정책) 을 이해 할 수 있다.



예를 들어보자면 Spring-boot 로 아무 셋팅을 하지 않고  Application을 실행 했을때 서버의 포트는 8080 이다.

하지만 React 의 기본 포트는 3000이다.

| Spring boot Appication | http:/localhost:8080     |
| ---------------------- | ------------------------ |
| **React**              | **http:/localhost:3000** |



자 React(Front-end) 에서  boot (Back-end) 로  [`XMLHttpRequest`](https://developer.mozilla.org/ko/docs/Web/API/XMLHttpRequest)를 사용하여 어떠한 자원을 요청 했을때 

정책을 위반함(**같은 포트가 아님**)으로 다음과 같은 이슈를 만나 볼 것 이다.



*has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource*



그럼 이 이슈를 해결하기위해선 어떻게 해야 할까?



해당 이슈를 해결 하는 방법은 많다. 



하지만 이번 글에서는 Spring-boot 내에서 해결하는 방법만 다룰 것이다.



이제 계속 CORS 를 알아보자.



## CORS(Cross-Origin Resource Sharing)



위에서 살펴 본 것처럼 기본 정책은 보안상 이유로 교차 HTTP 요청을 제한한다.



따라서 다른 웹 자원에 접근 하기위해선 권한을 부여해야 한다.

그러한 매커니즘을 지원 하는 것이 CORS(교차 출처 리소스 공유) 이다. 



#### **CORS 동작방법**

HTTP  요청 헤더에 Origin  출처를 서버에 전송하여

서버에서 Access-Control-Allow-Origin 안에 허용된 출처을 내려보내고

Orgin 필드와  Access-Control-Allow-Origin 과 비교하여 허용할 요청인지 허용하지 않을 요청인지 확인합니다.

![img](https://mdn.mozillademos.org/files/17214/simple-req-updated.png)



더 자세한 내용은 https://developer.mozilla.org/ko/docs/Web/HTTP/CORS 에서 확인 해주세요





자 그러면 이제 이슈를 해결 해보자.



```java
@RestController
public class SampleController {
    @GetMapping("CORS")
    public String cors(){
        return "CORS";
    }
}
```

여기 간단한 RestController 가 있다. (이서버는 8080 포트에 올릴 것이다.)





그리고 다른 서버(9999 port) 에서  8080 포트로  [`XMLHttpRequest`](https://developer.mozilla.org/ko/docs/Web/API/XMLHttpRequest)요청을 보내 보자.

XMLHttpRequest 요청은 JQuery 를 이용 하여 ajax 요청으로 요청을 보낼 것이다.

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>CORS</h1>
<script src="/webjars/jquery/3.5.1/dist/jquery.min.js"></script>
<script>
    $(function (){
        $.ajax("http://localhost:8080/CORS")
            .done(function (value){
                alert(value);
            })
            .fail(function (){
                alert("fail");
            });
    })
</script>
</body>
</html>
```



아직 서버 쪽에 아무런 설정을 하지 않아서  자원을 받아오지 못 한다.

![image](https://user-images.githubusercontent.com/64793712/105625095-211c9e00-5e6a-11eb-9d46-225de2e4852b.png)



이슈를 해결하기 위해선 Back-end 서버에서 다음과 같은 설정을 진행 해주면 된다.

```java
@Controller
public class SampleController {
    @GetMapping("CORS")
    @CrossOrigin(origins = "http://localhost:9999")  //요청 자원을 허락할 origin
    @ResponseBody
    public String cors(){
        return "CORS";
    }

}
```

`@CrossOrigin` 애노테이션을 이용 하면된다.



다음과 같이 설정하게 되면 http://localhost:8080/CORS 요청이 들어 왔을때  Origin 이 달라도 자원을 줄 수 있다.

만약  해당 프로젝트의 Controller 에서 전역적으로 사용할 

Origin을 등록하고 싶다면 WebMvcConfigurer 이용하여 설정해주면된다.



```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:9999");
    }
}
```


