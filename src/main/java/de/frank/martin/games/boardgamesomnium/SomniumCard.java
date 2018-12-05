package de.frank.martin.games.boardgamesomnium;

public class SomniumCard  {

    public enum CardColor {ANGEL, SUN, WAND, CUP, TOWER, SWORD, DRAGON, PENTACLE, MOON, DEMON}
    public enum CardType{ NUMBER, FOOL, THIEF}

    private CardColor cardColor;
    private CardType cardType;
    private Integer value;

    public SomniumCard(CardColor cardColor, CardType cardType, Integer value){
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.value = value;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return SomniumUtil.getCardColor(this)+"/"+SomniumUtil.getCardValue(this);
    }
}
