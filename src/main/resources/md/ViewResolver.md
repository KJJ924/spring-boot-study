# ViewResolver



스프링 부트를 이용하면  다음과 같이 전략을 선택 하여 등록할 수 있는데

![image](https://user-images.githubusercontent.com/64793712/104933177-ed65f200-59eb-11eb-9fad-58f11472b892.png)



이 중에서 ContentNegotiatingViewResolver 를 살펴보자.



ContentNegotiatingViewResolver 은 요청 Headers 정보중  Accept 를 확인하여 원하는 Data를 내려줄 수 있다. 

![image](https://user-images.githubusercontent.com/64793712/104935839-163bb680-59ef-11eb-9d76-54136f965669.png)

[Aceept 에 대한 링크](https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Accept)



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

## 