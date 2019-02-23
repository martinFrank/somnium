package de.frank.martin.games.boardgamesomnium;

import de.elite.games.cli.Command;
import de.elite.games.cli.CommandLineInterpreter;
import de.elite.games.cli.Response;
import de.frank.martin.games.boardgamelib.BaseBoardGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static de.frank.martin.games.boardgamesomnium.SomniumCard.CardType.FOOL;
import static de.frank.martin.games.boardgamesomnium.SomniumCard.CardType.THIEF;

public class SomniumGame extends BaseBoardGame<SomniumPlayer> implements CommandLineInterpreter<SomniumGame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SomniumGame.class);

    private final SomniumGameCommandLineInterpreter commandLineInterpreter;
    private SomniumCardDeck closedStack = new SomniumCardDeck();
    private boolean isTurnFailed = false;
    private SomniumCardDeck openStack = new SomniumCardDeck();

    SomniumGame() {
        commandLineInterpreter = new SomniumGameCommandLineInterpreter(this);
    }

    @Override
    public void endPlayersTurn(){
        if(!isTurnFailed()){
            LOGGER.debug("{} has been added to {}",openStack, getCurrent().getName());
            getCurrent().addCards(openStack);
        }else{
            LOGGER.debug("no cards for {}, the last card drawn was tainted, cards {} are lost", getCurrent().getName(),openStack);
        }
        openStack.clear();
        isTurnFailed = false;
        super.endPlayersTurn();
    }

    @Override
    public void initGame() {
        super.initGame();
        LOGGER.debug("init Game");
        closedStack.init();
        Collections.shuffle(getPlayers());
    }



    public void drawCard() {
        SomniumCard card = closedStack.drawCard();
        LOGGER.debug("{} has been drawn", card);
        openStack.add(card);
        checkFail(card);
    }

    private void checkFail(SomniumCard card) {
        if (card.isType(FOOL)) {
            LOGGER.debug("FOOL has been drawn, your turn is over", card);
            isTurnFailed = true;
            return;
        }
        Set<SomniumCard.CardColor> colors = new HashSet<>();
        if (openStack.containsColor(card.getCardColor())) {
            LOGGER.debug("Duplicate card color has been drawn, your turn is over", card);
            isTurnFailed = true;
        }
    }

    boolean isThiefOpen() {
        return openStack.isCardOpen(THIEF);
    }

    boolean isTurnFailed() {
        return isTurnFailed;
    }

    public void steal(SomniumCard.CardColor color) {
        openStack.removeLast(); //remove thief
        SomniumPlayer victim = getVictim();
        Optional<SomniumCard> card = victim.steal(color);
        if(card.isPresent()){
            victim.remove(card.get());
            openStack.add(card.get());
            checkFail(card.get());
            LOGGER.debug("{} was stolen from {} and brought back onto the table", card.get(), victim.getName());
        }
        LOGGER.debug("no valid card was stolen from {} ", victim.getName());
    }

    SomniumPlayer getVictim() {
        if(getPlayers().get(0).equals(getCurrent())){
            return getPlayers().get(1);
        }else{
            return getPlayers().get(0);
        }
    }

    public boolean allCardsAreDrawn() {
        //FIXME closedStack AND openStack must be empty???
        return closedStack.isEmpty();
    }

    SomniumCardDeck getOpenStack() {
        return openStack;
    }

    SomniumCardDeck getClosedDeck() {
        return closedStack;
    }

    @Override
    public Set<Command<SomniumGame>> getCommands() {
        return commandLineInterpreter.getCommands();
    }

    boolean hasCardsToDraw() {
        return !closedStack.isEmpty();
    }

    @Override
    public Response executeCommand(String identifier, List<String> parameter) {
        return commandLineInterpreter.executeCommand(identifier, parameter);
    }

    boolean isGameOver() {
        return closedStack.isEmpty() && openStack.isEmpty();
    }
}
