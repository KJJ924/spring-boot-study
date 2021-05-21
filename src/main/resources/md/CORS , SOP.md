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

