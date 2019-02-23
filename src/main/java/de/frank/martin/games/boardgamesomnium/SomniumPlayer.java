package de.frank.martin.games.boardgamesomnium;

import de.elite.games.cli.Command;
import de.frank.martin.games.boardgamelib.BasePlayer;
import de.frank.martin.games.boardgamesomnium.command.DrawCommand;
import de.frank.martin.games.boardgamesomnium.command.StealCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static de.frank.martin.games.boardgamesomnium.SomniumUtil.getMax;

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
                List<SomniumCard.CardColor> exclusiveColors =
                        new ArrayList<>(Arrays.asList(SomniumCard.CardColor.values()));
                exclusiveColors.removeAll(somniumGame.getOpenStack().stream().
                        map(SomniumCard::getCardColor).collect(Collectors.toList()));
                Collections.shuffle(exclusiveColors);
                SomniumCard.CardColor color = getBestColorFromOpponent(somniumGame, exclusiveColors);
                somniumGame.steal(color);
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

    private boolean hasOptions(Set<Command<SomniumGame>> commands) {
        return hasOptionSteal(commands) || hasOptionDraw(commands);
    }

    private boolean hasOptionSteal(Set<Command<SomniumGame>> commands) {
        return commands.stream().anyMatch(stealCommand::equals);
    }

    private boolean hasOptionDraw(Set<Command<SomniumGame>> commands) {
        return commands.stream().anyMatch(drawCommand::equals);
    }

    private SomniumCard.CardColor getBestColorFromOpponent(SomniumGame somniumGame, List<SomniumCard.CardColor> possibleColors) {
        int max = -1;
        SomniumCard.CardColor target = null;
        for (SomniumCard.CardColor color : possibleColors) {
            Integer value = getMax(somniumGame.getVictim().getCards().getCards(), color);
            if (value > max) {
                max = value;
                target = color;
            }
        }
        return target;
    }


    private int getDeltaFromOpenStack(SomniumGame somniumGame) {
        SomniumCardDeck currentCards = new SomniumCardDeck();
        int current = currentCards.getScore();
        currentCards.addAll(somniumGame.getOpenStack());
        int after = currentCards.getScore();
        return after - current;
    }

    SomniumCardDeck getCards() {
        return cards;
    }

    void addCards(SomniumCardDeck openCards) {
        cards.addAll(openCards);
    }


    public int getScore() {
        return cards.getScore();
    }

    public Optional<SomniumCard> steal(SomniumCard.CardColor color) {
        return cards.getCards().stream().filter(c -> color.equals(c.getCardColor())).
                max(Comparator.comparing(SomniumCard::getValue));
    }

    public void remove(SomniumCard somniumCard) {
        getCards().remove(somniumCard);
    }
}
