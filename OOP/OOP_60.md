# 객체 지향 프로그래밍

#### 60. 스트림    

자료의 대상과 관계없이 동일한 연산을 수행할 수 있는 기능(자료의 추상화)   
배열, 컬렉션에 동일한 연산이 수행되어 일관성 있는 처리 가능  
한번 생성하고 사용한 스트림은 재사용할 수 없음  
스트림 연산은 기존 자료를 변경하지 않음  
중간 연산과 최종 연산으로 구분 됨     
최종 연산이 수행되어야 모든 연산이 적용되는 지연 연산  

* 중간 연산     
filter(), map()     
조건에 맞는 요소를 추출하거나 요소를 변환 함   
문자열의 길이가 5이상인 요소만 출력하기  
```
sList.stream().filter(s->s.length()>=5).forEach(s->System.out.println(s));
```
고객 클래스에서 고객 이름만 가져오기    
```
customerList.stream().map(c->c.getName()).forEach(s->System.out.println(s));
```

* 최종 연산     
스트림의 자료를 소모하면서 연산을 수행   
최종 연산 후에 스트림은 더이상 다른 연산을 적용할 수 없음   
forEach() : 요소를 하나씩 꺼내 옴    
count() : 요소의 개수    
sum() : 요소의 합
등 여러 가지 최종 연산이 있음   

배열 스트림
```
package stream;

import java.util.Arrays;

public class IntArrayTest {
    public static void main(String[] args) {

        int[] arr = {1,2,3,4,5};

        int sum = Arrays.stream(arr).sum();
        int count = (int)Arrays.stream(arr).count(); //썼으니 재생성, long이므로 형변환

        System.out.println(sum);
        System.out.println(count);

    }
}
```

콜렉션 스트림
```
package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ArrayListStreamTest {
    public static void main(String[] args) {

        List<String> sList = new ArrayList<String>();
        sList.add("Tomas");
        sList.add("Edward");
        sList.add("Jack");

        Stream<String> stream = sList.stream(); //stream 객체 생성
        stream.forEach(s -> System.out.print(s + " "));
        System.out.println();

        sList.stream().sorted().forEach(s -> System.out.print(s + " ")); //sorted 중간 연산 추가
        System.out.println();

        sList.stream().map(s -> s.length()).forEach(n -> System.out.print(n + " ")); //map(변환) 중간 연산 추가

    }
}
```     
    
    