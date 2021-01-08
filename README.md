# spring-boot-study
[ Inflearn ] 스프링 부트 개념과 활용

# 1. 의존성 관리 이해

* 1. 스프링부트는 어떻게 해서 수 많은 의존성을 자동으로 가져올까?

![image](https://user-images.githubusercontent.com/64793712/104025027-5f2d8700-5207-11eb-810f-a99022c17716.png)  
정의한 의존성은 다음과 같이 2가지만 정의 되어 있고 의존성을 정의할때 버전도 쓰지 않는다.   
  
  
Spring boot 는 parent 폼에 이미 모든 버전이 명시가 되어 있다.  
![image](https://user-images.githubusercontent.com/64793712/104026532-68b7ee80-5209-11eb-92ab-8d1702703302.png)  
*<spring boot 2.4.1> 버전에 맞는 의존성을 이미 명시가 되어있음*

이러한 의존성 관리를 자동으로 해줌으로써 개발자가 관리해야하는 의존성이 줄어들게된다.


# 2. 의존성 관리 응용

## 의존성 추가방법  
  + 1-1 스프링 부트 parent 에서 관리하는 dependency 추가시.    
  ![image](https://user-images.githubusercontent.com/64793712/104026964-0e6b5d80-520a-11eb-9647-a4b65a498952.png)  
  *다음과 같이 원하는 dependency 를 추가하면된다. starter 같은경우   
  당연히 parent 폼에서 관리하는 의존성이기 때문에 버전을 명시하지 않아도된다.*  


  + 1-2 스프링 부트 parent 에서 관리하지 않는 dependency 추가시.   
  ![image](https://user-images.githubusercontent.com/64793712/104027410-b2550900-520a-11eb-9af6-1c164d8fe1de.png)  
  *스프링 부트에서 관리하지 않는 버전이기 때문에 반드시 버전을 명시해준다*
  
  + 1-3 스프링 부트에서 관리하는 의존성의 버전을 변경하고 싶은 경우.  
  ![image](https://user-images.githubusercontent.com/64793712/104028132-b3d30100-520b-11eb-949e-9c056ae17abc.png)  
  *properties 내에 변경하고 싶은 버전을 명시해주면 된다.*

  
  
