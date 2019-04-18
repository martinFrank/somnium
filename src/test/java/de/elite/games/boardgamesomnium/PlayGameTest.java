package de.elite.games.boardgamesomnium;

import org.junit.Assert;
import org.junit.Test;

public class PlayGameTest {

    @Test
    public void playGameTest() {


        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new TestGameSetup());
        somniumGame.initGame();
        while (!somniumGame.isGameOver()) {
            if (somniumGame.getCurrentPlayer().isHuman()) {
                somniumGame.endPlayersTurn();
            } else {
                somniumGame.getCurrentPlayer().performAiTurn();
            }
        }
        SomniumGamePrinter.printResults(System.out, somniumGame);
        Assert.assertTrue(somniumGame.isGameOver());


    }
}
