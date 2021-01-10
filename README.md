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

