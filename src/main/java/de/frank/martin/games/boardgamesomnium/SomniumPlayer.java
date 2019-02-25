package de.frank.martin.games.boardgamesomnium;

import de.elite.games.cli.Command;
import de.frank.martin.games.boardgamelib.BasePlayer;
import de.frank.martin.games.boardgamesomnium.command.DrawCommand;
import de.frank.martin.games.boardgamesomnium.command.StealCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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

        while (hasOptions(somniumGame.getCommands())) {
            if (hasOptionSteal(somniumGame.getCommands())) {
                List<SomniumCard> bestCards = getBestCardsFromOpponent(somniumGame);
                Collections.shuffle(bestCards);
                Optional<SomniumCard> card = bestCards.isEmpty() ? Optional.empty() : Optional.of(bestCards.get(0));
                somniumGame.steal(card);
            } else {
                int delta = getDeltaFromOpenStack(somniumGame);
                if (delta > 7) {
                    break;
                }
                if (somniumGame.getOpenStack().size() < 2) {
                    somniumGame.drawCard();
                    continue;
                }
                if (delta < 6 && somniumGame.getOpenStack().size() < 3) {
                    somniumGame.drawCard();
                    continue;
                }
                if (delta < 5 && somniumGame.getOpenStack().size() < 4) {
                    somniumGame.drawCard();
                    continue;
                }
                if (delta < 4 && somniumGame.getOpenStack().size() < 5) {
                    somniumGame.drawCard();
                    continue;
                }
                if (delta < 3 && somniumGame.getOpenStack().size() < 6) {
                    somniumGame.drawCard();
                    continue;
                }
                if (delta < 2 && somniumGame.getOpenStack().size() < 7) {
                    somniumGame.drawCard();
                    continue;
                }
                break;
            }
        }
        somniumGame.endPlayersTurn();
    }

    //@VisibleForTest
    List<SomniumCard> getBestCardsFromOpponent(SomniumGame somniumGame) {
        List<SomniumCard.CardColor> remaining = somniumGame.getOpenStack().getRemainingColors();
        SomniumPlayer victim = somniumGame.getVictim();
        return victim.getBestCards(remaining);
    }

    private List<SomniumCard> getBestCards(List<SomniumCard.CardColor> colors) {
        int bestValue = 0;
        List<SomniumCard> cards = new ArrayList<>();
        for (SomniumCard.CardColor color : colors) {
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

    private boolean hasOptions(Set<Command<SomniumGame>> commands) {
        return hasOptionSteal(commands) || hasOptionDraw(commands);
    }

    private boolean hasOptionSteal(Set<Command<SomniumGame>> commands) {
        return commands.stream().anyMatch(stealCommand::equals);
    }

    private boolean hasOptionDraw(Set<Command<SomniumGame>> commands) {
        return commands.stream().anyMatch(drawCommand::equals);
    }

    private SomniumCard getBestCardFromOpponent(SomniumGame somniumGame, List<SomniumCard.CardColor> possibleColors) {
        SomniumCard card = null;
        SomniumPlayer victim = somniumGame.getVictim();
        for (SomniumCard.CardColor color : possibleColors) {

            Optional<SomniumCard> candidate = victim.getBestCard(color);
            if (candidate.isPresent() && candidate.get().isMoreValuableThan(card)) {
                card = candidate.get();
            }
        }
        return card;
    }


    private int getDeltaFromOpenStack(SomniumGame somniumGame) {
        SomniumCardDeck currentCards = new SomniumCardDeck();
        int current = currentCards.getScore();
        currentCards.addAll(somniumGame.getOpenStack());
        int after = currentCards.getScore();
        return after - current;
    }

    void addCards(SomniumCardDeck openCards) {
        cards.addAll(openCards);
    }


    public int getScore() {
        return cards.getScore();
    }

    public Optional<SomniumCard> steal(SomniumCard card) {
        return cards.remove(card);
    }

    public Optional<SomniumCard> getBestCard(SomniumCard.CardColor color) {
        return cards.getBestCard(color);
    }
}
