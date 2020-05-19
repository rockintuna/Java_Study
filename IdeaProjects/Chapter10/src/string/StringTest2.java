package string;

public class StringTest2 {
    public static void main(String[] args) {

        String java = new String("java");
        String android = new String("android");

        System.out.println(System.identityHashCode(java));
        java = java.concat(android);

        System.out.println(java);
        System.out.println(System.identityHashCode(java)); //메모리 주소가 변경됨,즉 새로운 메모리 영역 사용됨

    }
}
