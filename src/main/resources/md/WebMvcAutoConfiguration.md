# WebMvcAutoConfiguration



spring-boot 가 자동설정 하는 Configuration 중  WebMvcAutoConfiguration 에 대해 서 알아보자.



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

