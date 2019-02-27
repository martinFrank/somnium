package de.frank.martin.games.boardgamesomnium;

import de.frank.martin.games.boardgamelib.BoardGameSetup;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DrawTest {

    @Test
    public void drawTest() {
        SomniumGame somniumGame = new SomniumFactory().create();

        SomniumCardDeck openDeck = somniumGame.getOpenDeck();

        Assert.assertTrue(somniumGame.getCommands().contains(PredefinedCommands.DRAW_COMMAND));

        openDeck.add(PredefinedCards.ANGEL_SEVEN);
        somniumGame.checkFail();
        Assert.assertTrue(somniumGame.getCommands().contains(PredefinedCommands.DRAW_COMMAND));

        openDeck.add(PredefinedCards.DEMON_SEVEN);
        somniumGame.checkFail();
        Assert.assertTrue(somniumGame.getCommands().contains(PredefinedCommands.DRAW_COMMAND));

        openDeck.add(PredefinedCards.ANGEL_SIX);
        somniumGame.checkFail();
        Assert.assertFalse(somniumGame.getCommands().contains(PredefinedCommands.DRAW_COMMAND));

    }


    @Test
    public void drawOverTest() {
        SomniumGame somniumGame = new SomniumFactory().create();

        Assert.assertTrue(somniumGame.getCommands().contains(PredefinedCommands.DRAW_COMMAND));
        SomniumCardDeck closedDeck = somniumGame.getClosedDeck();
        int amountCards = closedDeck.size();
        for (int i = 0; i < amountCards; i++) {
            closedDeck.removeLast();
        }
        Assert.assertFalse(somniumGame.getCommands().contains(PredefinedCommands.DRAW_COMMAND));

    }

    private BoardGameSetup<SomniumPlayer> getTestSetup() {

        return new BoardGameSetup<SomniumPlayer>() {

            @Override
            public List<SomniumPlayer> getPlayers() {
                ArrayList<SomniumPlayer> player = new ArrayList<>();
                player.add(new SomniumPlayer("YOU", 0xFFFF00, true));
                player.add(new SomniumPlayer("CPU", 0x0000FF, true));
                return player;
            }

            @Override
            public int getMaximumRounds() {
                return 0;
            }
        };
    }
}
