package com.github.martinfrank.somnium;

import org.junit.Assert;
import org.junit.Test;

public class RoundFinishedTest {

    @Test
    public void roundFinishedTest() {
        SomniumGame somniumGame = new SomniumFactory().create();
        somniumGame.getBoard().getOpenDeck().add(PredefinedCards.ANGEL_SEVEN);
//        somniumGame.checkFail();
        Assert.assertFalse(somniumGame.getBoard().isTurnFailed());
    }

    @Test
    public void roundFinishedDoubleTest() {
        SomniumGame somniumGame = new SomniumFactory().create();

        somniumGame.getBoard().getOpenDeck().add(PredefinedCards.ANGEL_SEVEN);
        somniumGame.getBoard().getOpenDeck().add(PredefinedCards.ANGEL_SIX);
//        somniumGame.checkFail();
        Assert.assertTrue(somniumGame.getBoard().isTurnFailed());
    }

    @Test
    public void roundFinishedFoolTest() {
        SomniumGame somniumGame = new SomniumFactory().create();

        somniumGame.getBoard().getOpenDeck().add(PredefinedCards.ANGEL_SEVEN);
        somniumGame.getBoard().getOpenDeck().add(PredefinedCards.FOOL);
//        somniumGame.checkFail();
        Assert.assertTrue(somniumGame.getBoard().isTurnFailed());
    }
}
