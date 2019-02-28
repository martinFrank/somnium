package de.frank.martin.games.boardgamesomnium;

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

        SomniumPlayer player = somniumGame.getCurrentPlayer();
        SomniumPlayer victim = somniumGame.getVictim();
        victim.addCards(cardDeck);

        somniumGame.getOpenDeck().add(PredefinedCards.THIEF);
        List<SomniumCard> cards = player.getBestCardsFromVictim(somniumGame);

        Assert.assertEquals(1, cards.size());
        Assert.assertEquals(PredefinedCards.ANGEL_SEVEN, cards.get(0));

        int before = victim.getScore();
        somniumGame.steal(cards.get(0));
        int after = victim.getScore();

        Assert.assertEquals(7, before - after);
        Assert.assertEquals(1, somniumGame.getOpenDeck().size()); // should be on the desk now
        Assert.assertTrue(somniumGame.getOpenDeck().getCards().contains(PredefinedCards.ANGEL_SEVEN));
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

        SomniumPlayer player = somniumGame.getCurrentPlayer();
        SomniumPlayer victim = somniumGame.getVictim();
        victim.addCards(cardDeck);

        somniumGame.getOpenDeck().add(PredefinedCards.THIEF);
        List<SomniumCard> cards = player.getBestCardsFromVictim(somniumGame);

        Assert.assertEquals(2, cards.size());
        Assert.assertTrue(cards.contains(goodA));
        Assert.assertTrue(cards.contains(goodB));
        Assert.assertFalse(cards.contains(badOne));

        int before = victim.getScore();
        somniumGame.steal(cards.get(0));
        int after = victim.getScore();

        Assert.assertEquals(7, before - after);
        Assert.assertEquals(1, somniumGame.getOpenDeck().size());
        Assert.assertFalse(somniumGame.getOpenDeck().getCards().contains(badOne));
    }
}
