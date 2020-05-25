package exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ThrowsException {

    public Class loadClass(String fileName, String className) throws FileNotFoundException, ClassNotFoundException { //throws로 미루기

        FileInputStream fis = new FileInputStream(fileName);
        Class c = Class.forName(className);
        return c;
    }
    public static void main(String[] args) {

        ThrowsException test = new ThrowsException();
        try {
            test.loadClass("IdeaProjects/Chapter13/a.txt","java.lang.String");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (Exception e) { //그 외 다른 예외 처리  (디폴트 익셉션)
            System.out.println(e);
        }
        System.out.println("end");
    }
}
