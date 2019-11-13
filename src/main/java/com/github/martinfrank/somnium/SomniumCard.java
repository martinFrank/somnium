package com.github.martinfrank.somnium;

public class SomniumCard {

    public enum CardColor {ANGEL, SUN, WAND, CUP, TOWER, SWORD, DRAGON, PENTACLE, MOON, DEMON}

    boolean isMoreValuableThan(SomniumCard card) {
        if (card == null) {
            return true;
        }
        if (card.cardColor == cardColor) {
            int current = card.getValue() != null ? card.getValue() : -1;
            return isMoreValuableThan(current);
        }
        return false;

    }

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

    boolean isMoreValuableThan(int currentValue) {
        int my = value == null ? 0 : value;
        return my > currentValue;
    }

    boolean isEqualValuable(int currentValue) {
        int my = value == null ? 0 : value;
        return my == currentValue;
    }

    boolean isOfColor(CardColor cardColor) {
        return this.cardColor == cardColor;
    }

    @Override
    public String toString() {
        return SomniumUtil.getCardColor(this) + "/" + SomniumUtil.getCardValue(this);
    }

    public enum CardType {NUMBER, FOOL, THIEF}
}
