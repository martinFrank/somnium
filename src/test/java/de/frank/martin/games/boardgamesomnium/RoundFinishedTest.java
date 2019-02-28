package de.frank.martin.games.boardgamesomnium;

import org.junit.Assert;
import org.junit.Test;

public class RoundFinishedTest {

    @Test
    public void roundFinishedTest() {
        SomniumGame somniumGame = new SomniumFactory().create();
        somniumGame.getOpenDeck().add(PredefinedCards.ANGEL_SEVEN);
//        somniumGame.checkFail();
        Assert.assertFalse(somniumGame.isTurnFailed());
    }

    @Test
    public void roundFinishedDoubleTest() {
        SomniumGame somniumGame = new SomniumFactory().create();

        somniumGame.getOpenDeck().add(PredefinedCards.ANGEL_SEVEN);
        somniumGame.getOpenDeck().add(PredefinedCards.ANGEL_SIX);
//        somniumGame.checkFail();
        Assert.assertTrue(somniumGame.isTurnFailed());
    }

    @Test
    public void roundFinishedFoolTest() {
        SomniumGame somniumGame = new SomniumFactory().create();

        somniumGame.getOpenDeck().add(PredefinedCards.ANGEL_SEVEN);
        somniumGame.getOpenDeck().add(PredefinedCards.FOOL);
//        somniumGame.checkFail();
        Assert.assertTrue(somniumGame.isTurnFailed());
    }
}
