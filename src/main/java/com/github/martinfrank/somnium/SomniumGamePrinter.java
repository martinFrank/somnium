package com.github.martinfrank.somnium;

import java.io.PrintStream;
import java.util.Optional;

import static com.github.martinfrank.somnium.SomniumUtil.*;

public class SomniumGamePrinter {

    private SomniumGamePrinter() {

    }

    public static void printGame(PrintStream out, SomniumBoard board) {
        out.println();
        SomniumPlayer topPlayer = board.getPlayers().get(0);
        SomniumPlayer bottomPlayer = board.getPlayers().get(1);
        out.println(" "+format(topPlayer.getName()) + ":" + topPlayer.getScore());
        printCardColors(out);
        printCardValues(out, topPlayer);
        out.println("\n");
        printTable(out, board);
        out.println("\n");
        out.println(" "+format(bottomPlayer.getName()) + ":" + bottomPlayer.getScore());
        printCardColors(out);
        printCardValues(out, bottomPlayer);
    }

    private static void printTable(PrintStream out, SomniumBoard board) {
        out.println(" " + format("Table") + ":" + board.getClosedDeck().size() + " cards left");
        out.println(getTableLine(board.getOpenDeck().size()));
        board.getOpenDeck().getCards().stream().
                map(card -> "|" + format(getCardColor(card))).forEach(out::print);
        out.println("|");
        out.println(getTableLine(board.getOpenDeck().size()));
        board.getOpenDeck().getCards().stream().
                map(card -> "|" +format(getCardValue(card) )).forEach(out::print);
        out.println("|");
        out.println(getTableLine(board.getOpenDeck().size()));
    }

    private static void printCardValues(PrintStream out, SomniumPlayer player) {
        for (SomniumCard.CardColor color : SomniumCard.CardColor.values()) {
            Optional<SomniumCard> card = player.getBestCard(color);
            int value = card.isPresent() ? card.get().getValue() : 0;
            out.print("|" + format(Integer.toString(value)));
        }
        out.println("|");
        out.println(getFullTableLine());
    }


    private static void printCardColors(PrintStream out) {
        out.println(getFullTableLine());
        for (SomniumCard.CardColor color : SomniumCard.CardColor.values()) {
            out.print("|" + format(color.toString()));
        }
        out.println("|");
        out.println(getFullTableLine());
    }

    public static void printResults(PrintStream out, SomniumBoard board) {
        printGame(out, board);
        out.println();
        out.println();
        out.println("--------------------------------------------------------");
        out.println("game over, score");
        board.getPlayers().stream().
                map(player -> "player " + player.getName() + " has " + player.getScore() + " score").
                forEach(out::println);
    }


}
