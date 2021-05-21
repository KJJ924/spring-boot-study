# Thymeleaf



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