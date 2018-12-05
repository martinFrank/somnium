package de.frank.martin.games.boardgamesomnium;

import java.util.List;

class SomniumUtil {

    private SomniumUtil(){

    }

    static String formatToEight(String str) {
        String eight = ""+str;
        while(eight.length()<8){
            eight = eight +" ";
        }
        return eight;
    }

    static Integer getMax(List<SomniumCard> cards, SomniumCard.CardColor color) {
        Integer max = 0;
        for (SomniumCard card: cards ){
            if (card.getCardColor() == color && card.getValue() > max){
                max = card.getValue();
            }
        }
        return max;
    }
}
