# Java 기초

#### 07. 문자 자료형

인코딩 : 문자 -> 숫자값(코드)     
'A' -> 65   
디코딩 : 코드 -> 문자      
65 -> 'A'   

문자세트 (code-set)
1. 아스키(ASCII) : 1byte 영문자, 숫자, 특수문자 표현
2. 유니코드(Unicode) : 한글 등 복잡한 언어 표현 
'가' -> AC00 (2byte)

```
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
```

(int)로 코드 확인 가능    
(char)로 코드를 문자로 확인 가능  
char type은 음수 불가능   
문자형 자료에 '\u'를 추가하여 Unicode 의 코드 사용   
char는 2byte인데 '한글'은 4byte이므로 불가능
    
    