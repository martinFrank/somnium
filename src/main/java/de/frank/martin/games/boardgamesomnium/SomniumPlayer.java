package de.frank.martin.games.boardgamesomnium;

import de.elite.games.cli.CommandMapping;
import de.frank.martin.games.boardgamelib.BasePlayer;
import de.frank.martin.games.boardgamesomnium.command.DrawCommand;
import de.frank.martin.games.boardgamesomnium.command.StealCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SomniumPlayer extends BasePlayer<SomniumGame> {

    private static final Logger LOG = LoggerFactory.getLogger(BasePlayer.class);
    private final DrawCommand drawCommand = new DrawCommand();
    private final StealCommand stealCommand = new StealCommand();

    private SomniumCardDeck cards = new SomniumCardDeck();

    SomniumPlayer(String name, int color, boolean isHuman) {
        super(name, color, isHuman);
    }

    @Override
    public void performAiTurn() {
        LOG.debug("{} is working on its somnium move", getName());
        SomniumGame somniumGame = getBoardGame();
        somniumGame.startPlayersTurn();
        somniumGame.drawCard();


        int cardCounter = 0;
        while (hasOptions(somniumGame.getCommands())) {
            if (hasOptionSteal(somniumGame.getCommands())) {
                stealCardFromOpponent(somniumGame);
            } else {
                if (wantsToDrawMoreCards(somniumGame, cardCounter)) {
                    cardCounter++;
                    somniumGame.drawCard();
                    continue;
                }
                break;
            }
        }
        somniumGame.endPlayersTurn();
    }

    private boolean wantsToDrawMoreCards(SomniumGame somniumGame, int cardCounter) {
        int delta = getDeltaFromOpenStack(somniumGame);
        int breakEven = 7 - cardCounter;
        int amount = cardCounter + 2;
        if (delta < breakEven && somniumGame.getOpenDeck().size() < amount) {
            LOG.debug("myMethod: ");
            return true;
        }
        return false;
    }

    private void stealCardFromOpponent(SomniumGame somniumGame) {
        List<SomniumCard> bestCards = getBestCardsFromVictim(somniumGame);
        Collections.shuffle(bestCards);
        Optional<SomniumCard> card = bestCards.isEmpty() ? Optional.empty() : Optional.of(bestCards.get(0));
        if (card.isPresent()) {
            somniumGame.steal(card.get());
        }
    }

    //@VisibleForTest
    List<SomniumCard> getBestCardsFromVictim(SomniumGame somniumGame) {
        List<SomniumCard.CardColor> remaining = somniumGame.getOpenDeck().getRemainingColors();
        SomniumPlayer victim = somniumGame.getVictim();
        return victim.getBestCards(remaining);
    }

    private List<SomniumCard> getBestCards(List<SomniumCard.CardColor> allowedColors) {
        int bestValue = 0;
        List<SomniumCard> cards = new ArrayList<>();
        for (SomniumCard.CardColor color : allowedColors) {
            Optional<SomniumCard> bestOfColor = getBestCard(color);
            if (bestOfColor.isPresent()) {
                if (bestOfColor.get().isMoreValuableThan(bestValue)) {
                    cards.clear();
                    bestValue = bestOfColor.get().getValue();
                }
                if (bestOfColor.get().isEqualValuable(bestValue)) {
                    cards.add(bestOfColor.get());
                }
            }
        }
        return cards;
    }

    private boolean hasOptions(CommandMapping commands) {
        return hasOptionSteal(commands) || hasOptionDraw(commands);
    }

    private boolean hasOptionSteal(CommandMapping commands) {
        return commands.hasCommands(stealCommand.getIdentifier());
    }

    private boolean hasOptionDraw(CommandMapping commands) {
        return commands.hasCommands(drawCommand.getIdentifier());
    }


    private int getDeltaFromOpenStack(SomniumGame somniumGame) {
        SomniumCardDeck currentCards = new SomniumCardDeck();
        int current = currentCards.getScore();
        currentCards.addAll(somniumGame.getOpenDeck());
        int after = currentCards.getScore();
        return after - current;
    }

    void addCards(SomniumCardDeck openCards) {
        cards.addAll(openCards);
    }

    int getScore() {
        return cards.getScore();
    }

    Optional<SomniumCard> steal(SomniumCard card) {
        return cards.remove(card);
    }

    public Optional<SomniumCard> getBestCard(SomniumCard.CardColor color) {
        return cards.getBestCard(color);
    }
}
