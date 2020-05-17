package codingexample;

public class ArrayTest { //char 배열에 알파벳 나열

    public static void main(String[] args) {

        int[] iArr = new int[26];
        char[] cArr = new char[26];

        for (int i=0,num=65; i<iArr.length; i++,num++) {
            iArr[i] = num;
        }

        for (int i=0; i<iArr.length; i++) {
            cArr[i]=(char)iArr[i];
        }

        for (int i=0; i<cArr.length; i++) {
            System.out.println(cArr[i]);
        }

    }

}
