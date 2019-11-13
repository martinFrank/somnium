package com.github.martinfrank.somnium;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class StealTest {

    @Test
    public void stealTestSingle() {
        SomniumGame somniumGame = new SomniumFactory().create();

        SomniumCardDeck cardDeck = new SomniumCardDeck();
        cardDeck.add(PredefinedCards.ANGEL_SEVEN);
        cardDeck.add(PredefinedCards.CUP_ONE);

        SomniumPlayer player = somniumGame.getBoard().getCurrentPlayer();
        SomniumPlayer victim = somniumGame.getBoard().getVictim();
        victim.addCards(cardDeck);

        somniumGame.getBoard().getOpenDeck().add(PredefinedCards.THIEF);
        List<SomniumCard> cards = player.getBestCardsFromVictim(somniumGame.getBoard());

        Assert.assertEquals(1, cards.size());
        Assert.assertEquals(PredefinedCards.ANGEL_SEVEN, cards.get(0));

        int before = victim.getScore();
        somniumGame.getBoard().steal(cards.get(0));
        int after = victim.getScore();

        Assert.assertEquals(7, before - after);
        Assert.assertEquals(1, somniumGame.getBoard().getOpenDeck().size()); // should be on the desk now
        Assert.assertTrue(somniumGame.getBoard().getOpenDeck().getCards().contains(PredefinedCards.ANGEL_SEVEN));
    }

    @Test
    public void stealTestDouble() {
        SomniumGame somniumGame = new SomniumFactory().create();

        SomniumCard goodA = PredefinedCards.ANGEL_SEVEN;
        SomniumCard goodB = PredefinedCards.DEMON_SEVEN;
        SomniumCard badOne = PredefinedCards.CUP_ONE;
        SomniumCardDeck cardDeck = new SomniumCardDeck();
        cardDeck.add(goodA);
        cardDeck.add(goodB);
        cardDeck.add(badOne);

        SomniumPlayer player = somniumGame.getBoard().getCurrentPlayer();
        SomniumPlayer victim = somniumGame.getBoard().getVictim();
        victim.addCards(cardDeck);

        somniumGame.getBoard().getOpenDeck().add(PredefinedCards.THIEF);
        List<SomniumCard> cards = player.getBestCardsFromVictim(somniumGame.getBoard());

        Assert.assertEquals(2, cards.size());
        Assert.assertTrue(cards.contains(goodA));
        Assert.assertTrue(cards.contains(goodB));
        Assert.assertFalse(cards.contains(badOne));

        int before = victim.getScore();
        somniumGame.getBoard().steal(cards.get(0));
        int after = victim.getScore();

        Assert.assertEquals(7, before - after);
        Assert.assertEquals(1, somniumGame.getBoard().getOpenDeck().size());
        Assert.assertFalse(somniumGame.getBoard().getOpenDeck().getCards().contains(badOne));
    }
}
