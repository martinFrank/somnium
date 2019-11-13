package com.github.martinfrank.somnium;

import org.junit.Assert;
import org.junit.Test;

public class DrawTest {

    @Test
    public void drawTest() {
        SomniumGame somniumGame = new SomniumFactory().create();

        SomniumCardDeck openDeck = somniumGame.getBoard().getOpenDeck();

        Assert.assertTrue(somniumGame.getCommands().hasCommands(PredefinedCommands.DRAW_COMMAND.getIdentifier()));

        openDeck.add(PredefinedCards.ANGEL_SEVEN);
        Assert.assertTrue(somniumGame.getCommands().hasCommands(PredefinedCommands.DRAW_COMMAND.getIdentifier()));

        openDeck.add(PredefinedCards.DEMON_SEVEN);
        Assert.assertTrue(somniumGame.getCommands().hasCommands(PredefinedCommands.DRAW_COMMAND.getIdentifier()));

        openDeck.add(PredefinedCards.ANGEL_SIX);
        Assert.assertFalse(somniumGame.getCommands().hasCommands(PredefinedCommands.DRAW_COMMAND.getIdentifier()));

    }


    @Test
    public void drawOverTest() {
        SomniumGame somniumGame = new SomniumFactory().create();

        Assert.assertTrue(somniumGame.getCommands().hasCommands(PredefinedCommands.DRAW_COMMAND.getIdentifier()));
        SomniumCardDeck closedDeck = somniumGame.getBoard().getClosedDeck();
        int amountCards = closedDeck.size();
        for (int i = 0; i < amountCards; i++) {
            closedDeck.removeLast();
        }
        Assert.assertFalse(somniumGame.getCommands().hasCommands(PredefinedCommands.DRAW_COMMAND.getIdentifier()));

    }

}
