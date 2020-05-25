package exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionTest {
    public static void main(String[] args) {

        try(FileInputStream fis = new FileInputStream("IdeaProjects/Chapter13/a.txt")) {
        } catch (FileNotFoundException e) { //File이 없을 때
            System.out.println(e);
        } catch (IOException e) { //close() 예외 발생 시
            System.out.println(e);
        }
        System.out.println("end");
    }
}
