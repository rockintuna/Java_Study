package lambda;

@FunctionalInterface //함수형 인터페이스라고 전달
                     //메서드 두개 이상 불가능
public interface MyMaxNumber {
    int getMaxNumber(int x, int y);
    
}
