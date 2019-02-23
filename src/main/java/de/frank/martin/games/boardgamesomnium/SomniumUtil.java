package de.frank.martin.games.boardgamesomnium;

import java.util.List;

class SomniumUtil {

    private final static int LENGTH_SHORT = 8;

    private SomniumUtil(){

    }

    static String format(String str) {
        return format(str, LENGTH_SHORT);
    }

    private static String format(String str, int length) {
        StringBuilder sb = new StringBuilder(str);
        while(sb.length()<length){
            sb.append(" ");
        }
        return sb.toString();
    }

    static Integer getMax(List<SomniumCard> cards, SomniumCard.CardColor color) {
        Integer max = 0;
        for (SomniumCard card : cards) {
            if (card.getCardColor() == color && card.getValue() > max){
                max = card.getValue();
            }
        }
        return max;
    }
    static String getCardColor(SomniumCard somniumCard) {
        return somniumCard.getCardColor() == null ? "XXX" : somniumCard.getCardColor().toString();
    }

    static String getCardValue(SomniumCard card) {
        return card.getValue() == null ? card.getCardType().toString() : Integer.toString(card.getValue());
    }

    static String getTableLine(int amount){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            sb.append("+--------");
        }
        sb.append("+");
        return sb.toString();
    }

    static String getFullTableLine(){
        return getTableLine(10);
    }
}
