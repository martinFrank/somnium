package com.github.martinfrank.somnium;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;

public class PlayGameTest {

    @Test
    public void playGameTest() {
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.getBoard().setup(new TestGameSetup());
        somniumGame.getBoard().initGame();
        while (!somniumGame.getBoard().isGameOver()) {
            if (somniumGame.getBoard().getCurrentPlayer().isHuman()) {
                somniumGame.getBoard().endPlayersTurn();
            } else {
                somniumGame.getBoard().getCurrentPlayer().performAiTurn();
            }
        }
        somniumGame.getCommands().findCommand("result").ifPresent(c -> c.execute(Collections.emptyList()));
        SomniumGamePrinter.printResults(System.out, somniumGame.getBoard());
        Assert.assertTrue(somniumGame.getBoard().isGameOver());

        Optional<Command> restartCommand = somniumGame.getCommands().findCommand("restart");
        if (restartCommand.isPresent()) {
            Response response = restartCommand.get().execute(Collections.emptyList());
            Assert.assertFalse(response.failed());
        } else {
            Assert.fail();
        }
    }

    @Test
    public void mainTest() {
        try {
            InputStream original = System.in;
            System.setIn(new ByteArrayInputStream("exit\n".getBytes()));
            App.main(new String[]{});
            System.setIn(original);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
