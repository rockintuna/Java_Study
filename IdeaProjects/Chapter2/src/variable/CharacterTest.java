package variable;

public class CharacterTest {
    public static void main(String[] args) {

        char ch = 'A';
        System.out.println(ch);
        System.out.println((int)ch);

        int iCh = 66;
        System.out.println((char)iCh);

        //char ch2 = -66;

        char hangul = '\uAC00';
        System.out.println(hangul);

        //char hangul2 = '한글';
        char hangul2 = '한';
        System.out.println(hangul2);
    }
}
