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
