package de.frank.martin.games.boardgamesomnium;

import de.elite.games.cli.CommandList;
import de.elite.games.cli.CommandProvider;
import de.frank.martin.games.boardgamelib.BaseBoardGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Optional;

import static de.frank.martin.games.boardgamesomnium.SomniumCard.CardType.FOOL;
import static de.frank.martin.games.boardgamesomnium.SomniumCard.CardType.THIEF;

public class SomniumGame extends BaseBoardGame<SomniumPlayer> implements CommandProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SomniumGame.class);

    private final SomniumGameCommandProvider somniumGameCommandProvider;
    private SomniumCardDeck closedDeck = new SomniumCardDeck();
    private SomniumCardDeck openDeck = new SomniumCardDeck();

    SomniumGame() {
        somniumGameCommandProvider = new SomniumGameCommandProvider(this);
    }

    @Override
    public void endPlayersTurn() {
        if (!isTurnFailed()) {
            LOGGER.debug("{} has been added to {}", openDeck, getCurrentPlayer().getName());
            getCurrentPlayer().addCards(openDeck);
        } else {
            LOGGER.debug("no cards for {}, the last card drawn was tainted, cards {} are lost", getCurrentPlayer().getName(), openDeck);
        }
        openDeck.clear();
        super.endPlayersTurn();
    }

    @Override
    public void initGame() {
        super.initGame();
        LOGGER.debug("init Game");
        closedDeck.init();
        Collections.shuffle(getPlayers());
        SomniumPlayer player = getCurrentPlayer();
        LOGGER.debug("starting player is {}", player.getName());
        if (!player.isHuman()) {
            player.performAiTurn();
        }
    }

    /**
     * draw a card from the closedDeck and put it to the openDeck
     */
    public void drawCard() {
        SomniumCard card = closedDeck.drawCard();
        LOGGER.debug("{} has been drawn", card);
        openDeck.add(card);
        if (isTurnFailed()) {
            LOGGER.debug("ooohhh your turn is over now...");
        }
    }

    boolean isThiefOpen() {
        return openDeck.isCardOpen(THIEF);
    }

    boolean isTurnFailed() {
        if (openDeck.isCardOpen(FOOL)) {
            LOGGER.debug("FOOL has been drawn, your turn is over");
            return true;
        }
        if (!openDeck.areColorsUnique()) {
            LOGGER.debug("Duplicate card color has been drawn, your turn is over");
            return true;
        }
        return false;
    }

    public void steal(SomniumCard card) {
        openDeck.removeLast(); //remove thief from deck
        SomniumPlayer victim = getVictim();
        Optional<SomniumCard> opt = victim.steal(card);
        if (opt.isPresent()) {
            openDeck.add(opt.get());
            LOGGER.debug("{} was stolen from {} and brought back onto the table", opt.get(), victim.getName());
        } else {
            LOGGER.debug("no valid card was stolen ...");
        }
        if (isTurnFailed()) {
            LOGGER.debug("ooohhh your turn is over now...");
        }

    }

    public SomniumPlayer getVictim() {
        if (getPlayers().get(0).equals(getCurrentPlayer())) {
            return getPlayers().get(1);
        } else {
            return getPlayers().get(0);
        }
    }

    public boolean allCardsAreDrawn() {
        return closedDeck.isEmpty();
    }

    SomniumCardDeck getOpenDeck() {
        return openDeck;
    }

    SomniumCardDeck getClosedDeck() {
        return closedDeck;
    }

    boolean isGameOver() {
        return closedDeck.isEmpty() && openDeck.isEmpty();
    }

    @Override
    public CommandList getCommands() {
        return somniumGameCommandProvider.getCommands();
    }

    boolean hasCardsToDraw() {
        return !closedDeck.isEmpty();
    }

    boolean hasCardBeenDrawn() {
        return getOpenDeck().size() > 0;
    }
}
