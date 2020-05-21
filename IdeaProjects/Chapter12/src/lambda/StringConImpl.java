package lambda;

public class StringConImpl implements StringConcat{
    @Override
    public void makeString(String str1, String str2) {
        System.out.println(str1+" "+str2);
    }
}
