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

    SomniumCard(CardType cardType) {
        if (cardType != CardType.FOOL && cardType != CardType.THIEF) {
            throw new IllegalArgumentException("invalid constructor - only FOOL/THIEF allowed");
        }
        this.cardColor = null;
        this.cardType = cardType;
        this.value = null;
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


    public boolean isMoreValuableThan(SomniumCard card) {
        if (card == null) {
            return true;
        }
        if (card.cardColor == cardColor) {
            //FIXME possible NPE
            return value > card.value;
        }
        return false;

    }

    public boolean isMoreValuableThan(int currentValue) {
        return value > currentValue;
    }

    public boolean isEqualValuable(int currentValue) {
        return value == currentValue;
    }

    public boolean isOfColor(CardColor cardColor) {
        return this.cardColor == cardColor;
    }

    @Override
    public String toString() {
        return SomniumUtil.getCardColor(this)+"/"+SomniumUtil.getCardValue(this);
    }
}
