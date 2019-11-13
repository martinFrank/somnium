package com.github.martinfrank.somnium;

import com.github.martinfrank.boardgamelib.BasePlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SomniumPlayer extends BasePlayer<SomniumBoard> {

    private static final Logger LOG = LoggerFactory.getLogger(BasePlayer.class);

    private SomniumCardDeck cards = new SomniumCardDeck();

    SomniumPlayer(String name, int color, boolean isHuman) {
        super(name, color, isHuman);
    }

    @Override
    public void performAiTurn() {
        LOG.debug("{} is working on its somnium move", getName());
        SomniumBoard board = getBoardGame();
        board.startPlayersTurn();
        board.drawCard();


        int cardCounter = 0;
        while (hasOptions(board)) {
            if (board.isThiefOpen()) {
                stealCardFromOpponent(board);
            } else {
                if (wantsToDrawMoreCards(board, cardCounter)) {
                    cardCounter++;
                    board.drawCard();
                    continue;
                }
                break;
            }
        }
        board.endPlayersTurn();
    }

    private boolean wantsToDrawMoreCards(SomniumBoard board, int cardCounter) {
        int delta = getDeltaFromOpenStack(board);
        int breakEven = 7 - cardCounter;
        int amount = cardCounter + 2;
        if (delta < breakEven && board.getOpenDeck().size() < amount) {
            LOG.debug("myMethod: ");
            return true;
        }
        return false;
    }

    private void stealCardFromOpponent(SomniumBoard board) {
        List<SomniumCard> bestCards = getBestCardsFromVictim(board);
        Collections.shuffle(bestCards);
        Optional<SomniumCard> card = bestCards.isEmpty() ? Optional.empty() : Optional.of(bestCards.get(0));
        card.ifPresent(board::steal);
    }

    //@VisibleForTest
    List<SomniumCard> getBestCardsFromVictim(SomniumBoard board) {
        List<SomniumCard.CardColor> remaining = board.getOpenDeck().getRemainingColors();
        SomniumPlayer victim = board.getVictim();
        return victim.getBestCards(remaining);
    }

    private List<SomniumCard> getBestCards(List<SomniumCard.CardColor> allowedColors) {
        int bestValue = 0;
        List<SomniumCard> bestCards = new ArrayList<>();
        for (SomniumCard.CardColor color : allowedColors) {
            Optional<SomniumCard> bestOfColor = getBestCard(color);
            if (bestOfColor.isPresent()) {
                if (bestOfColor.get().isMoreValuableThan(bestValue)) {
                    bestCards.clear();
                    bestValue = bestOfColor.get().getValue();
                }
                if (bestOfColor.get().isEqualValuable(bestValue)) {
                    bestCards.add(bestOfColor.get());
                }
            }
        }
        return bestCards;
    }

    private boolean hasOptions(SomniumBoard board) {
        return board.isThiefOpen() || board.hasCardsToDraw();
    }

    private int getDeltaFromOpenStack(SomniumBoard board) {
        SomniumCardDeck currentCards = new SomniumCardDeck();
        int current = currentCards.getScore();
        currentCards.addAll(board.getOpenDeck());
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
