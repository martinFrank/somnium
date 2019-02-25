package de.frank.martin.games.boardgamesomnium;

import org.junit.Assert;
import org.junit.Test;

public class RoundFinishedTest {

    @Test
    public void roundFinishedTest() {
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();

        SomniumCard angelSeven = new SomniumCard(SomniumCard.CardColor.ANGEL, SomniumCard.CardType.NUMBER, 7);

        somniumGame.getOpenStack().add(angelSeven);
        somniumGame.checkFail();
        Assert.assertFalse(somniumGame.isTurnFailed());
    }

    @Test
    public void roundFinishedDoubleTest() {
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();

        SomniumCard angelSeven = new SomniumCard(SomniumCard.CardColor.ANGEL, SomniumCard.CardType.NUMBER, 7);
        SomniumCard angelSix = new SomniumCard(SomniumCard.CardColor.ANGEL, SomniumCard.CardType.NUMBER, 6);

        somniumGame.getOpenStack().add(angelSeven);
        somniumGame.getOpenStack().add(angelSix);
        somniumGame.checkFail();
        Assert.assertTrue(somniumGame.isTurnFailed());
    }

    @Test
    public void roundFinishedFoolTest() {
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();

        SomniumCard angelSeven = new SomniumCard(SomniumCard.CardColor.ANGEL, SomniumCard.CardType.NUMBER, 7);
        SomniumCard fool = new SomniumCard(SomniumCard.CardType.FOOL);

        somniumGame.getOpenStack().add(angelSeven);
        somniumGame.getOpenStack().add(fool);
        somniumGame.checkFail();
        Assert.assertTrue(somniumGame.isTurnFailed());
    }
}
