package de.frank.martin.games.boardgamesomnium;

import de.frank.martin.games.boardgamelib.BasePlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static de.frank.martin.games.boardgamesomnium.SomniumCommand.*;
import static de.frank.martin.games.boardgamesomnium.SomniumUtil.getMax;

public class SomniumPlayer extends BasePlayer<SomniumGame> {

    private static Logger LOG = LoggerFactory.getLogger(BasePlayer.class);

    private final List<SomniumCard> cards = new ArrayList<>();

    public SomniumPlayer(String name, int color, boolean isHuman) {
        super(name, color, isHuman);
    }

    @Override
    public void performAiTurn() {
        LOG.debug("{} is working on its somnium move", getName());
        SomniumGame somniumGame = getBoardGame();
        somniumGame.startPlayersTurn();
        somniumGame.drawCard();

        while (somniumGame.getPossibleCommands().contains(DRAW) || somniumGame.getPossibleCommands().contains(STEAL)) {
            if (somniumGame.getPossibleCommands().contains(STEAL)) {
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

    private SomniumCard.CardColor getBestColorFromOpponent(SomniumGame somniumGame, List<SomniumCard.CardColor> possibleColors) {
        int max = -1;
        SomniumCard.CardColor target = null;
        for (SomniumCard.CardColor color : possibleColors) {
            Integer value = getMax(somniumGame.getVictim().getCards(), color);
            if (value > max) {
                max = value;
                target = color;
            }
        }
        return target;
    }


    private int getDeltaFromOpenStack(SomniumGame somniumGame) {
        List<SomniumCard> currentCards = new ArrayList<>(getCards());
        int current = getScore(currentCards);
        currentCards.addAll(somniumGame.getOpenStack());
        int after = getScore(currentCards);
        return after - current;
    }

    public List<SomniumCard> getCards() {
        return cards;
    }

    public void addCards(List<SomniumCard> openCards) {
        cards.addAll(openCards);
    }

    public int getScore() {
        return getScore(getCards());
    }

    private int getScore(List<SomniumCard> cards) {
        return Arrays.stream(SomniumCard.CardColor.values()).
                map(color -> getMax(cards, color)).mapToInt(value -> value).sum();
    }
}
