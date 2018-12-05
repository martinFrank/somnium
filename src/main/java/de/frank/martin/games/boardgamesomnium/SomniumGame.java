package de.frank.martin.games.boardgamesomnium;

import de.frank.martin.games.boardgamelib.BaseBoardGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static de.frank.martin.games.boardgamesomnium.SomniumCard.CardType.*;
import static de.frank.martin.games.boardgamesomnium.SomniumCommand.*;

public class SomniumGame extends BaseBoardGame<SomniumPlayer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SomniumGame.class);

    private final List<SomniumCard> cards = createCards();
    private final List<SomniumCard> closedStack = new ArrayList<>();
    private final List<SomniumCard> openStack = new ArrayList<>();
    private boolean isTurnFailed = false;

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
        closedStack.clear();
        closedStack.addAll(cards);
        Collections.shuffle(closedStack);
        Collections.shuffle(getPlayers());
    }



    private List<SomniumCard> createCards() {
        List<SomniumCard> cards = new ArrayList<>();
        for (int i = 0; i < 7; i ++){
            for (SomniumCard.CardColor color: SomniumCard.CardColor.values()){
                cards.add(new SomniumCard(color, NUMBER, i+1));
            }
        }
        for(int i = 0; i < 2; i ++){
            cards.add(new SomniumCard(null, FOOL, null));
            cards.add(new SomniumCard(null, THIEF, null));
        }
        return cards;
    }

    public void drawCard() {
        SomniumCard card = closedStack.remove(0);
        LOGGER.debug("{} has been drawn", card);
        openStack.add(card);
        checkFail(card);
    }

    private void checkFail(SomniumCard card) {
        if(card.getCardType() == FOOL){
            LOGGER.debug("FOOL has been drawn, your turn is over", card);
            isTurnFailed = true;
            return;
        }
        Set<SomniumCard.CardColor> colors = new HashSet<>();
        for (SomniumCard openCard: openStack){
            if (colors.contains(openCard.getCardColor())){
                LOGGER.debug("Duplicate card color has been drawn, your turn is over", card);
                isTurnFailed = true;
            }else{
                colors.add(openCard.getCardColor());
            }
        }
    }


    public List<SomniumCommand> getPossibleCommands() {
        if(isTurnFailed()){
            return Arrays.asList(DONE, SHOW, EXIT);
        }
        if(isThiefOpen()){
            return Arrays.asList(STEAL, SHOW, EXIT);
        }
        if (!closedStack.isEmpty()){
            return Arrays.asList(DONE, DRAW, SHOW, EXIT);
        }
        return Arrays.asList(DONE, SHOW, EXIT);
    }

    private boolean isThiefOpen() {
        return openStack.stream().anyMatch(e -> THIEF.equals(e.getCardType()));
    }

    private boolean isTurnFailed() {
        return isTurnFailed;
    }

    public void steal(SomniumCard.CardColor color) {
        openStack.remove(openStack.size()-1);
        SomniumPlayer victim = getVictim();
        Optional<SomniumCard> card = victim.getCards().stream().filter(c -> color.equals(c.getCardColor())).
                max(Comparator.comparing(SomniumCard::getValue));
        if(card.isPresent()){
            victim.getCards().remove(card.get());
            openStack.add(card.get());
            checkFail(card.get());
            LOGGER.debug("{} was stolen from {} and brought back onto the table", card.get(), victim.getName());
        }
        LOGGER.debug("no valid card was stolen from {} ", victim.getName());
    }

    public SomniumPlayer getVictim() {
        if(getPlayers().get(0).equals(getCurrent())){
            return getPlayers().get(1);
        }else{
            return getPlayers().get(0);
        }
    }

    public boolean allCardsAreDrawn() {
        return closedStack.isEmpty();
    }

    public List<SomniumCard> getOpenStack() {
        return Collections.unmodifiableList(openStack);
    }

    public List<SomniumCard> getClosedStack() {
        return Collections.unmodifiableList(closedStack);
    }

}
