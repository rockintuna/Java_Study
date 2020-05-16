# 객체 지향 프로그래밍

#### 38. 인터페이스를 활용한 다형성 구현  

인터페이스의 역할 : 
클라이언트에 어떤 메서드를 제공하는지 알려주는 명세    
한 객체가 어떤 인터페이스 타입이라는 것은 그 인터페이스의 메서드를 구현했다는 의미  
클라이언트 프로그램은 실제 구현내용을 몰라도 인터페이스의 정의만 알면 그 객체를 사용할 수 있다.  

ex) JDBC Lib의 Connect 인터페이스에 대한 실제 구현은 DB사에서하며 개발자는 구현내용을 몰라도 됨.  

```
package scheduler;

public interface Scheduler {
    public void getNextCall();
    public void sendCallToAgent();
}
```

```
package scheduler;

public class RoundRobin implements Scheduler{
    @Override
    public void getNextCall() {
        System.out.println("상담 전화를 순서대로 대기열에서 가져옵니다.");
    }

    @Override
    public void sendCallToAgent() {
        System.out.println("다음 순서의 상담원에게 배분합니다.");
    }
}
```

```
package scheduler;

import java.io.IOException;

public class SchedulerTest {
    public static void main(String[] args) throws IOException {

        System.out.println("전화 상담원 할당 방식을 선택하세요");
        System.out.println("R : 한명씩 차례대로");
        System.out.println("L : 대기가 적은 상담원 우선");
        System.out.println("P : 우선순위 높은고객 우선 숙련도 높은 상담원");

        int ch = System.in.read();
        Scheduler scheduler = null;
        if (ch == 'R' || ch == 'r') {
            scheduler = new RoundRobin();
        } else if (ch == 'L' || ch == 'l') {
            scheduler = new LeastJob();
        } else if (ch == 'P' || ch == 'p') {
            scheduler = new PriorityAllocation();
        } else {
            System.out.println("Error");
            return;
        }

        scheduler.getNextCall();
        scheduler.sendCallToAgent();
    }
}
```

* Strategy Pattern  
인터페이스를 이용하면 다양한 정책이나 알고리즘을 프로그램의 큰 수정 없이 적용, 확장할 수 있다.  
    
    