package com.github.martinfrank.somnium;

public class PredefinedCards {

    static final SomniumCard ANGEL_SEVEN = new SomniumCard(SomniumCard.CardColor.ANGEL, SomniumCard.CardType.NUMBER, 7);
    static final SomniumCard ANGEL_SIX = new SomniumCard(SomniumCard.CardColor.ANGEL, SomniumCard.CardType.NUMBER, 6);
    static final SomniumCard DEMON_SEVEN = new SomniumCard(SomniumCard.CardColor.DEMON, SomniumCard.CardType.NUMBER, 7);
    static final SomniumCard CUP_ONE = new SomniumCard(SomniumCard.CardColor.CUP, SomniumCard.CardType.NUMBER, 1);
    static final SomniumCard FOOL = new SomniumCard(SomniumCard.CardType.FOOL);
    static final SomniumCard THIEF = new SomniumCard(SomniumCard.CardType.THIEF);

    private PredefinedCards() {

    }
}
