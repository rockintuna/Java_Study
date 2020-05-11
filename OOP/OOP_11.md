# 객체 지향 프로그래밍

#### 11. this에 대하여

* this의 역할  

자신의 메모리를 가리킴    
```
public static void main(String[] args) {
    BirthDay day = new BirthDay();
    day.setYear(2000); //setYear 메서드에 this가 있을때 생성된 인스턴스(day)의 메모리
}
```
생성자에서 다른 생성자를 호출 함  
```
public Person() {
    //age = 10;     //this로 다른 생성자를 호출할때 그위에 다른 statement는 올 수 없다.
    this("이름 없음",1);  //아래의 생성자 호출하여 변수 초기화
}

public Person(String name,int age) {
    this.name = name;
    this.age = age;
}
```

인스턴스 자신의 주소를 반환     
```
    public Person getSelf() { //반환 타입은 자기 클래스 자신
        return this;
    }
```

```
        Person personLee = new Person("Lee",20);
        System.out.println(personLee);
        //위와 아래는 같은 결과 (인스턴스의 메모리 주소)
        Person p = personLee.getSelf();
        System.out.println(p);

```
