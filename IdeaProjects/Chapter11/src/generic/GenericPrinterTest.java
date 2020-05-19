package generic;

public class GenericPrinterTest {
    public static void main(String[] args) {

        GenericPrinter<Powder> powderPrinter = new GenericPrinter<>();
        Powder powder = new Powder();
        powderPrinter.setMeterial(powder);
        System.out.println(powderPrinter.toString());

        GenericPrinter<Plastic> plasticPrinter = new GenericPrinter<>();
        Plastic plastic = new Plastic();
        plasticPrinter.setMeterial(plastic);
        System.out.println(plasticPrinter.toString());

        //GenericPrinter<Water> waterPrinter; //Water는 Meterial 클래스를 상속받지 않았으므로 에러 발생

        plasticPrinter.printing();
        powderPrinter.printing();
    }
}
