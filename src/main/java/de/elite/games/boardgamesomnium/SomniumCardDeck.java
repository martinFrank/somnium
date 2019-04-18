package de.elite.games.boardgamesomnium;

import java.util.*;
import java.util.stream.Collectors;

import static de.elite.games.boardgamesomnium.SomniumCard.CardType.*;
import static de.elite.games.boardgamesomnium.SomniumUtil.getMax;

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
            cards.add(new SomniumCard(FOOL));
            cards.add(new SomniumCard(THIEF));
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

    public boolean areColorsUnique() {
        List<SomniumCard.CardColor> colors = new ArrayList<>();
        for (SomniumCard card : deck) {
            SomniumCard.CardColor color = card.getCardColor();
            if (colors.contains(color)) {
                return false;
            } else {
                colors.add(color);
            }
        }
        return true;
    }

    public void clear() {
        deck.clear();
    }


    public void addAll(SomniumCardDeck other) {
        deck.addAll(other.deck);
    }

    public int getScore() {
        return Arrays.stream(SomniumCard.CardColor.values()).
                map(color -> getMax(deck, color)).mapToInt(value -> value).sum();
    }

    public Optional<SomniumCard> remove(SomniumCard somniumCard) {
        if (deck.contains(somniumCard)) {
            deck.remove(somniumCard);
            return Optional.of(somniumCard);
        }
        return Optional.empty();
    }

    public Optional<SomniumCard> getBestCard(SomniumCard.CardColor color) {
        SomniumCard card = null;
        for (SomniumCard candidate : allOfColor(color)) {
            if (candidate.isMoreValuableThan(card)) {
                card = candidate;
            }
        }
        return card == null ? Optional.empty() : Optional.of(card);
    }

    private List<SomniumCard> allOfColor(SomniumCard.CardColor cardColor) {
        return deck.stream().filter(c -> c.isOfColor(cardColor)).collect(Collectors.toList());
    }


    @Override
    public String toString() {
        return deck.toString();
    }

    List<SomniumCard.CardColor> getRemainingColors() {
        List<SomniumCard.CardColor> colors = new ArrayList<>(Arrays.asList(SomniumCard.CardColor.values()));
        colors.removeAll(getColors());
        return colors;
    }

    private List<SomniumCard.CardColor> getColors() {
        List<SomniumCard.CardColor> colors = new ArrayList<>();
        for (SomniumCard card : deck) {
            SomniumCard.CardColor color = card.getCardColor();
            if (!colors.contains(color)) {
                colors.add(color);
            }
        }
        return colors;
    }

    public List<SomniumCard> getCards() {
        return deck;
    }
}
