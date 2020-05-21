package lambda;

public class TestStringConcat {
    public static void main(String[] args) {

        StringConImpl impl = new StringConImpl();
        impl.makeString("hello","world");

        StringConcat concat = (s,v) -> System.out.println(s+" "+v); //람다식 사용
        concat.makeString("hello","world"); //클래스 구현이 필요없고, 메서드 구현을 따로 만들 필요도 없음.
                                            //실제로는 아래처럼 익명 내부클래스로 동작한다.

        StringConcat concat2 = new StringConcat() {
            @Override
            public void makeString(String str1, String str2) {
                System.out.println(str1+" "+str2);
            }
        };
        concat2.makeString("hello","world");
    }
}
