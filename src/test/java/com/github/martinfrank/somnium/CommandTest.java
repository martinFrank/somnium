package com.github.martinfrank.somnium;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

public class CommandTest {

    @Test
    public void testCommands() {

        SomniumGame somniumGame = new SomniumGame();
        somniumGame.getBoard().setup(new TestGameSetup());
        somniumGame.getBoard().initGame();

        Optional<Command> drawCommand = somniumGame.getCommands().findCommand("draw");
        if (drawCommand.isPresent()) {
            Response response = drawCommand.get().execute(Collections.emptyList());
            Assert.assertFalse(response.failed());
        } else {
            Assert.fail();
        }

        Optional<Command> showCommand = somniumGame.getCommands().findCommand("show");
        if (showCommand.isPresent()) {
            Response response = showCommand.get().execute(Collections.emptyList());
            Assert.assertFalse(response.failed());
        } else {
            Assert.fail();
        }

        Optional<Command> endTurnCommand = somniumGame.getCommands().findCommand("done");
        if (endTurnCommand.isPresent()) {
            Response response = endTurnCommand.get().execute(Collections.emptyList());
            Assert.assertFalse(response.failed());
        } else {
            Assert.fail();
        }


    }

    @Test
    public void stealCommandTest() {
        SomniumGame somniumGame = new SomniumFactory().create();

        SomniumCardDeck cardDeck = new SomniumCardDeck();
        cardDeck.add(PredefinedCards.ANGEL_SEVEN);
        cardDeck.add(PredefinedCards.CUP_ONE);

        SomniumPlayer player = somniumGame.getBoard().getCurrentPlayer();
        SomniumPlayer victim = somniumGame.getBoard().getVictim();
        victim.addCards(cardDeck);

        somniumGame.getBoard().getOpenDeck().add(PredefinedCards.THIEF);

        Optional<Command> stealCommand = somniumGame.getCommands().findCommand("steal");
        Assert.assertTrue(stealCommand.isPresent());

        Response response = stealCommand.get().execute(Collections.singletonList("ANGEL"));
        Assert.assertFalse(response.failed());


    }
}
