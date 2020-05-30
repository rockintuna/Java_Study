# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 02. What, How, Hello world

* 무엇을 만들 것인가    
프로그래밍은 프로그램을 만드는 것이며, 이는 문제를 해결하는 것이다.  
무엇을 만들 것인가의 시작은 무엇이 문제인가로부터 시작된다.   

* 사용자 스토리   
사용자 입장에서 프로그램의 기능을 서술한 것
(사용자)는 (가치)를 위해 (기능)을 할 수 있다.   
ex) <고객>은 <뭘 먹고 싶은지 발견>할 수 있도록 <가게 목록을 볼> 수 있다.

* 계획을 세워야 한다    
사용자 스토리 기반에서 어떤 순서로 개발할 것인지 세운다.    
계획을 세워두면 변경이 있을 떄 얼마나 기간이 늘어날 지, 또는 어떤 것을 포기할지 선택하는 기준이 된다.      

* 어떻게 만들 것인가    
도메인 모델링 : 해결하려는 문제에서 쓰이는 개념들을 정의하고 필요한 것을 알아보는것     
모델 : 가게, 메뉴 정보, 사용자, 즐겨찾기 정보, 리뷰 정보, 예약 정보
시스템 아키텍쳐 : 시스템의 구성, 제약 조건을 알아야 한다.  
제약 조건 : 홈페이지를 통해 서비스(Web), 모바일(Mobile App)  
3-tier Architecture : 가장 흔하게 쓰이는 시스템 아키텍쳐   
Presentation : 사용자와 소통하는 부분 (Front-end|HTML,CSS,JS)     
Business : 소통의 결과, 사용자 요청 처리 등 (Back-end|Rest API)   
Data source : 처리된 데이터가 저장되는 곳 (Database|DBMS)    

* Back-end를 어떻게 만들 것인가  
Layered Architecture (4개의 층으로 구성됨)  
    >UI Layer    
    >>Application Layer   
    >>>Domain Layer    
    >>>>Infrastructure Layer

    각 레이어는 아래의 레이어에 의존하고 위의 레이어를 사용하지 않는다.    
 
* 기술 선택     
JAVA, Spring Boot, Rest API     

* 프로젝트 생성   
스프링 부트 프로젝트 생성 (Spring Initailizer 이용)  
[Spring Home page](https://spring.io/) > Projects > Spring Boot > Quickstart > 여러 선택 후 GENERATE > IDEA에서 open

UI 레이어를 위한 interfaces 패키지 추가 및 Welcome 컨트롤러 생성    
```
package kr.co.fastcampus.eatgo.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //웹에서 접속 가능한 컨트롤러 어노테이션
public class WelcomController {

    @GetMapping("/") //http에서 접속하는데 쓰는 기본적인 4가지 메소드중 하나
    public String hello() {
        return "Hello, world!!!";
    }
} 
```
    
    