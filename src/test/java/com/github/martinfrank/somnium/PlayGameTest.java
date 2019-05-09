package com.github.martinfrank.somnium;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

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
        somniumGame.getCommands().findCommand("result").ifPresent(c -> c.execute(Collections.emptyList()));
        SomniumGamePrinter.printResults(System.out, somniumGame);
        Assert.assertTrue(somniumGame.isGameOver());

        Optional<Command> restartCommand = somniumGame.getCommands().findCommand("restart");
        if (restartCommand.isPresent()) {
            Response response = restartCommand.get().execute(Collections.emptyList());
            Assert.assertFalse(response.failed());
        } else {
            Assert.fail();
        }
    }
}
