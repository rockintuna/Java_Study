package string;

public class StringBuilderTest {
    public static void main(String[] args) {

        String java = new String("java");
        String android = new String("android");

        StringBuilder buffer = new StringBuilder(java);
        System.out.println(System.identityHashCode(buffer));
        buffer.append("android");
        System.out.println(System.identityHashCode(buffer)); //변경되어도 메모리 주소는 변하지 않음

        java = buffer.toString(); //String 타입으로 형변환
        System.out.println(java);
    }
}
