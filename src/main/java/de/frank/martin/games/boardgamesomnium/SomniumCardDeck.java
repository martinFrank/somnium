package de.frank.martin.games.boardgamesomnium;

import java.util.*;
import java.util.stream.Stream;

import static de.frank.martin.games.boardgamesomnium.SomniumCard.CardType.*;
import static de.frank.martin.games.boardgamesomnium.SomniumUtil.getMax;

public class SomniumCardDeck {

    private final List<SomniumCard> deck = new ArrayList<>();

    public static List<SomniumCard> createSomniumCardDeck() {
        List<SomniumCard> cards = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (SomniumCard.CardColor color : SomniumCard.CardColor.values()) {
                cards.add(new SomniumCard(color, NUMBER, i + 1));
            }
        }
        for (int i = 0; i < 2; i++) {
            cards.add(new SomniumCard(null, FOOL, null));
            cards.add(new SomniumCard(null, THIEF, null));
        }
        return cards;
    }

    public void init() {
        deck.clear();
        deck.addAll(createSomniumCardDeck());
        Collections.shuffle(deck);
    }

    public SomniumCard drawCard() {
        return deck.remove(0);
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public int size() {
        return deck.size();
    }

    public void add(SomniumCard card) {
        deck.add(card);
    }

    public boolean isCardOpen(SomniumCard.CardType card) {
        return deck.stream().anyMatch(e -> card.equals(e.getCardType()));
    }

    public void removeLast() {
        deck.remove(deck.size() - 1);
    }

    public Stream<SomniumCard> stream() {
        return deck.stream();
    }

    public boolean containsColor(SomniumCard.CardColor color) {
        Set<SomniumCard.CardColor> colors = new HashSet<>();
        for (SomniumCard openCard : deck) {
            if (colors.contains(openCard.getCardColor())) {
                return true;
            } else {
                colors.add(openCard.getCardColor());
            }
        }
        return false;
    }

    public void clear() {
        deck.clear();
    }

    public List<SomniumCard> getCards() {
        return Collections.unmodifiableList(deck);
    }

    public void addAll(SomniumCardDeck other) {
        deck.addAll(other.deck);
    }

    public int getScore() {
        return Arrays.stream(SomniumCard.CardColor.values()).
                map(color -> getMax(deck, color)).mapToInt(value -> value).sum();
    }

    public void remove(SomniumCard somniumCard) {
        deck.remove(somniumCard);
    }
}
