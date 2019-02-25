package de.frank.martin.games.boardgamesomnium;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class StealTest {

    @Test
    public void stealTestSingle() {
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();

        SomniumCard goodOne = new SomniumCard(SomniumCard.CardColor.ANGEL, SomniumCard.CardType.NUMBER, 7);
        SomniumCard badOne = new SomniumCard(SomniumCard.CardColor.CUP, SomniumCard.CardType.NUMBER, 1);
        SomniumCardDeck cardDeck = new SomniumCardDeck();
        cardDeck.add(goodOne);
        cardDeck.add(badOne);

        SomniumPlayer player = somniumGame.getCurrent();
        SomniumPlayer victim = somniumGame.getVictim();
        victim.addCards(cardDeck);

        somniumGame.drawCard(); //let's assert we did draw THIEF/XXX
        List<SomniumCard> cards = player.getBestCardsFromOpponent(somniumGame);

        Assert.assertEquals(1, cards.size());
        Assert.assertEquals(goodOne, cards.get(0));

        int before = victim.getScore();
        somniumGame.steal(Optional.of(cards.get(0)));
        int after = victim.getScore();

        Assert.assertEquals(7, before - after);
        Assert.assertEquals(1, somniumGame.getOpenStack().size()); // should be on the desk now
    }

    @Test
    public void stealTestDouble() {
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();

        SomniumCard goodA = new SomniumCard(SomniumCard.CardColor.ANGEL, SomniumCard.CardType.NUMBER, 7);
        SomniumCard goodB = new SomniumCard(SomniumCard.CardColor.DEMON, SomniumCard.CardType.NUMBER, 7);
        SomniumCard badOne = new SomniumCard(SomniumCard.CardColor.CUP, SomniumCard.CardType.NUMBER, 1);
        SomniumCardDeck cardDeck = new SomniumCardDeck();
        cardDeck.add(goodA);
        cardDeck.add(goodB);
        cardDeck.add(badOne);

        SomniumPlayer player = somniumGame.getCurrent();
        SomniumPlayer victim = somniumGame.getVictim();
        victim.addCards(cardDeck);

        somniumGame.drawCard(); //let's assert we did draw THIEF/XXX
        List<SomniumCard> cards = player.getBestCardsFromOpponent(somniumGame);

        Assert.assertEquals(2, cards.size());
        Assert.assertTrue(cards.contains(goodA));
        Assert.assertTrue(cards.contains(goodB));

        int before = victim.getScore();
        somniumGame.steal(Optional.of(cards.get(0)));
        int after = victim.getScore();

        Assert.assertEquals(7, before - after);
        Assert.assertEquals(1, somniumGame.getOpenStack().size()); // should be on the desk now
    }
}
