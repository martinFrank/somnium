package de.elite.games.boardgamesomnium;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

public class CommandTest {

    @Test
    public void testCommands() {

        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new TestGameSetup());
        somniumGame.initGame();

        Optional<Command> drawCommand = somniumGame.getCommands().findCommand("draw");
        if (drawCommand.isPresent()) {
            Response response = drawCommand.get().execute(Collections.emptyList());
            Assert.assertFalse(response.failed());
        }

        Optional<Command> showCommand = somniumGame.getCommands().findCommand("show");
        if (showCommand.isPresent()) {
            Response response = showCommand.get().execute(Collections.emptyList());
            Assert.assertFalse(response.failed());
        }

        Optional<Command> endTurnCommand = somniumGame.getCommands().findCommand("done");
        if (endTurnCommand.isPresent()) {
            Response response = endTurnCommand.get().execute(Collections.emptyList());
            Assert.assertFalse(response.failed());
        }

        Optional<Command> restartCommand = somniumGame.getCommands().findCommand("restart");
        if (restartCommand.isPresent()) {
            Response response = restartCommand.get().execute(Collections.emptyList());
            Assert.assertFalse(response.failed());
        }

    }

    @Test
    public void stealCommandTest() {
        SomniumGame somniumGame = new SomniumFactory().create();

        SomniumCardDeck cardDeck = new SomniumCardDeck();
        cardDeck.add(PredefinedCards.ANGEL_SEVEN);
        cardDeck.add(PredefinedCards.CUP_ONE);

        SomniumPlayer player = somniumGame.getCurrentPlayer();
        SomniumPlayer victim = somniumGame.getVictim();
        victim.addCards(cardDeck);

        somniumGame.getOpenDeck().add(PredefinedCards.THIEF);

        Optional<Command> stealCommand = somniumGame.getCommands().findCommand("steal");
        Assert.assertTrue(stealCommand.isPresent());

        Response response = stealCommand.get().execute(Collections.singletonList("ANGEL"));
        Assert.assertFalse(response.failed());


    }
}
