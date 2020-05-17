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

    public void readBooks() {
        System.out.println("사람이 책을 읽습니다.");
    }
}

class Tiger extends Animal {

    @Override
    public void move() {
        System.out.println("호랑이가 네 발로 뜁니다.");
    }

    public void hunting() {
        System.out.println("호랑이가 사냥을 합니다.");
    }
}

class Eagle extends Animal {

    @Override
    public void move() {
        System.out.println("독수리가 하늘을 날아갑니다.");
    }

    public void flying() {
        System.out.println("독수리가 날개를 쭉 펴고 멀리 날아갑니다.");
    }
}

public class AnimalTest {

    public static void main(String[] args) {

        Animal hAnimal = new Human();
        Animal tAnimal = new Tiger();
        Animal eAnimal = new Eagle();

        //Eagle eagle = (Eagle)hAnimal; //Error발생, Human 인스턴스를 Eagle로 형변환하려고함
                                        //다운캐스팅은 새로운 선언type과 캐스팅type만 비교하므로 경고가 없기때문에 이런 문제가 발생할 수 있음.
        /*if (hAnimal instanceof Eagle) {    //inatanceof는 인스턴스의 타입을 확인하여 true,false 반환
            Eagle eagle = (Eagle)hAnimal;  //실행되지 않기 때문에 Error 없음.
            eagle.flying();
        } else if (hAnimal instanceof Human) {
            Human human1 = (Human)hAnimal;
            human1.readBooks();
        }*/

        ArrayList<Animal> animalList = new ArrayList<Animal>();
        animalList.add(hAnimal);
        animalList.add(tAnimal);
        animalList.add(eAnimal);

        AnimalTest test = new AnimalTest();
        test.testDownCasting(animalList);
    }

    public void testDownCasting(ArrayList<Animal> list) {
        for(int i=0; i<list.size(); i++) {  //Animal 인스턴스들의 type을 확인한 후 다운캐스팅하는 for문
           Animal animal = list.get(i);

           if (animal instanceof Human) {
               Human human = (Human)animal;
               human.readBooks();
           } else if (animal instanceof Tiger) {
               Tiger tiger = (Tiger)animal;
               tiger.hunting();
           } else if (animal instanceof Eagle) {
               Eagle eagle = (Eagle)animal;
               eagle.flying();
           } else {
               System.out.println("Error");
           }
        }
    }

    public void moveAnimal(Animal animal) {
        animal.move();
    }

}
