package staticex;

public class Company {

    private static Company instance = new Company(); //내부적으로 인스턴스 생성

    private Company() { //생성자 private로 생성
    }

    public static Company getInstance() {  //외부에서 인스턴스 생성과 무관하게 사용하기 위해 static으로 생성
        if (instance==null) {
            instance = new Company();
        }
        return instance;
    }

}
