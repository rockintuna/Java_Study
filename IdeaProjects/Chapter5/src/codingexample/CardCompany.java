package codingexample;

public class CardCompany {

    private static int serialNum=10000;

    private static CardCompany instance = new CardCompany();

    private CardCompany() {}

    public static CardCompany getInstance() {
        return instance;
    }

    public Card createCard() {
        Card card = new Card();
        serialNum++;
        card.setCardNumber(serialNum);
        return card;
    }

}
