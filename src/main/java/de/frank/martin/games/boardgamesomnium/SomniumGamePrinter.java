package de.frank.martin.games.boardgamesomnium;

import java.io.PrintStream;
import java.util.stream.IntStream;

import static de.frank.martin.games.boardgamesomnium.SomniumUtil.formatToEight;
import static de.frank.martin.games.boardgamesomnium.SomniumUtil.getMax;

class SomniumGamePrinter {

    private static final String LINE_SEP =
            "+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+";

    private final SomniumGame somniumGame;

    SomniumGamePrinter(SomniumGame somniumGame) {
        this.somniumGame = somniumGame;
    }

    void printGame(PrintStream out) {
        out.println();
        out.println();
        out.println();
        out.println();
        out.println();
        SomniumPlayer topPlayer = somniumGame.getPlayers().get(0);
        SomniumPlayer bottomPlayer = somniumGame.getPlayers().get(1);
        out.println(" "+formatToEight(topPlayer.getName()) + ":" + topPlayer.getScore());
        printCardColors(out);
        printCardValues(out, topPlayer);
        out.println("\n");
        printTable(out);
        out.println("\n");
        out.println(" "+formatToEight(bottomPlayer.getName()) + ":" + bottomPlayer.getScore());
        printCardColors(out);
        printCardValues(out, bottomPlayer);
    }

    private void printTable(PrintStream out) {
        out.println(" "+formatToEight("Table") + ":" + somniumGame.getClosedStack().size() + " cards left");

        //Start
        for (int i = 0; i < somniumGame.getOpenStack().size(); i++) {
            out.print("+--------");
        }
        out.println("+");

        //-------cards start
        for (int i = 0; i < somniumGame.getOpenStack().size(); i++) {
            SomniumCard.CardColor cardColor = somniumGame.getOpenStack().get(i).getCardColor();
            String colorString = cardColor == null ? "XXX" : cardColor.toString();
            out.print("|" + formatToEight(colorString));
        }
        out.println("|");


        IntStream.range(0, somniumGame.getOpenStack().size()).mapToObj(i -> "+--------").forEach(out::print);
        out.println("+");
        for (int i = 0; i < somniumGame.getOpenStack().size(); i++) {
            Integer cardValue = somniumGame.getOpenStack().get(i).getValue();
            String cardString = cardValue == null ? somniumGame.getOpenStack().get(i).getCardType().toString() : Integer.toString(cardValue);
            out.print("|" + formatToEight(cardString));
        }
        out.println("|");

        //-------cards end

        //end
        for (int i = 0; i < somniumGame.getOpenStack().size(); i++) {
            out.print("+--------");
        }
        out.println("+");
    }

    private void printCardValues(PrintStream out, SomniumPlayer player) {
        for (SomniumCard.CardColor color : SomniumCard.CardColor.values()) {
            Integer value = getMax(player.getCards(), color);
            out.print("|" + formatToEight(value.toString()));
        }
        out.println("|");
        out.println(LINE_SEP);
    }


    private void printCardColors(PrintStream out) {
        out.println(LINE_SEP);
        for (SomniumCard.CardColor color : SomniumCard.CardColor.values()) {
            String cardColor = formatToEight(color.toString());
            out.print("|" + cardColor);
        }
        out.println("|");
        out.println(LINE_SEP);
    }

    void showResults(PrintStream out) {
        printGame(out);
        out.println();
        out.println();
        out.println("--------------------------------------------------------");
        out.println("game over, score");
        for (SomniumPlayer player : somniumGame.getPlayers()) {
            System.out.println("player " + player.getName() + " has " + player.getScore() + " score");
        }
    }
}
