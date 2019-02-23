package de.frank.martin.games.boardgamesomnium;

public class SomniumCard  {


    public enum CardColor {ANGEL, SUN, WAND, CUP, TOWER, SWORD, DRAGON, PENTACLE, MOON, DEMON}

    public enum CardType{ NUMBER, FOOL, THIEF}

    private final CardColor cardColor;

    private final CardType cardType;
    private final Integer value;

    SomniumCard(CardColor cardColor, CardType cardType, Integer value) {
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.value = value;
    }

    CardColor getCardColor() {
        return cardColor;
    }

    CardType getCardType() {
        return cardType;
    }

    Integer getValue() {
        return value;
    }

    public boolean isType(CardType type) {
        return getCardType() == type;
    }

    @Override
    public String toString() {
        return SomniumUtil.getCardColor(this)+"/"+SomniumUtil.getCardValue(this);
    }
}
