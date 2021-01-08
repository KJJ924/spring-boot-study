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

