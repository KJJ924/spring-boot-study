# ExceptionHandler



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





## **@ControllerAdvice**

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

