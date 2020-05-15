# 객체 지향 프로그래밍

#### 28. 다형성    

* 다형성 이란    
하나의 코드가 여러 자료형으로 구현되어 실행되는 것    
같은 코드에서 여러 실행 결과가 나옴    

정보은닉, 상속과 더불어 객체지향 프로그래밍의 가장 큰 특징 중 하나이다.   
OOP의 유연성, 재활용성, 유지보수성에 기본이 되는 특징이다.     

* 다형성의 장점   
유사한 클래스가 추가되는 경우의 유지보수에 용이하고    
각 자료형 마다 다른 메서드를 호출하지 않으므로 코드에서 많은 if문이 사라진다.   

```
package polymorphism;

import java.util.ArrayList;

class Animal {

    public void move() {
        System.out.println("동물이 움직입니다.");
    }
}

class Human extends Animal {

    @Override
    public void move() {
        System.out.println("사람이 두 발로 걷습니다.");
    }
}

class Tiger extends Animal {

    @Override
    public void move() {
        System.out.println("호랑이가 네 발로 뜁니다.");
    }
}

class Eagle extends Animal {

    @Override
    public void move() {
        System.out.println("독수리가 하늘을 날아갑니다.");
    }
}

public class AnimalTest {

    public static void main(String[] args) {

        Animal hAnimal = new Human();
        Animal tAnimal = new Tiger();
        Animal eAnimal = new Eagle();

        /*AnimalTest test = new AnimalTest();
        test.moveAnimal(hAnimal);
        test.moveAnimal(tAnimal);
        test.moveAnimal(eAnimal);*/

        ArrayList<Animal> animalList = new ArrayList<Animal>();
        animalList.add(hAnimal);
        animalList.add(tAnimal);
        animalList.add(eAnimal);

        for(Animal animal : animalList) {
            animal.move(); //다형성 
        }
    }

    public void moveAnimal(Animal animal) {
        animal.move(); //하나의 코드에서 여러 자료형을 구현하여 여러 결과가 나옴. (다형성)
                       //매개변수는 Animal이지만 실제 호된 move는 Human, Tiger, Eagle의 메서드. (업캐스팅,오버라이딩)
                       //여러 클래스가 Animal이라는 타입 하나로 수행될 수 있음.
    }

}
```
    
    