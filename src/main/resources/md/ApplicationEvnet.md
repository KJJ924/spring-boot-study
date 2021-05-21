# ApplicationEvent 



Spring core 부분을 공부하면서 ApplicationContext  Interface 가 상속 받은 Interface 중에 ApplicationEventPublisher 를 상속 받고 있다는 것을 알고 있다.

<img src="https://user-images.githubusercontent.com/64793712/104308256-b7200280-5513-11eb-9f52-2de1ccbb6525.png" alt="image" style="zoom:150%;" />



## 그러면 **ApplicationEventPublisher** 의 역할은 무엇을 하는 것일까 ?



ApplicationEventPublisher 은  <u>옵저버 패턴의 구현체로 이벤트 프로그래밍에 필요한 기능</u>을 제공한다.



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

따라서 SampleListener 을 만들거나

```java
@Component
public class SampleListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        System.out.println("ApplicationContext 가 생성하기전에 출력해 ");
    }
}
```



ApplicationListener  인터페이스는  FunctionalInterface 임으로 람다식으로 클래스를 만들지 않고도 사용할 수 있다.

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



