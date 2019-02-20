package de.frank.martin.games.boardgamesomnium;

import java.io.PrintStream;

import static de.frank.martin.games.boardgamesomnium.SomniumUtil.*;

public class SomniumGamePrinter {

    public static void printGame(PrintStream out, SomniumGame somniumGame) {
//        out.println();
//        out.println();
//        out.println();
//        out.println();
        out.println();
        SomniumPlayer topPlayer = somniumGame.getPlayers().get(0);
        SomniumPlayer bottomPlayer = somniumGame.getPlayers().get(1);
        out.println(" "+format(topPlayer.getName()) + ":" + topPlayer.getScore());
        printCardColors(out);
        printCardValues(out, topPlayer);
        out.println("\n");
        printTable(out, somniumGame);
        out.println("\n");
        out.println(" "+format(bottomPlayer.getName()) + ":" + bottomPlayer.getScore());
        printCardColors(out);
        printCardValues(out, bottomPlayer);
    }

    private static void printTable(PrintStream out, SomniumGame somniumGame) {
        out.println(" "+format("Table") + ":" + somniumGame.getClosedStack().size() + " cards left");
        out.println(getTableLine(somniumGame.getOpenStack().size()));
        somniumGame.getOpenStack().stream().
                map(card -> "|" + format(getCardColor(card))).forEach(out::print);
        out.println("|");
        out.println(getTableLine(somniumGame.getOpenStack().size()));
        somniumGame.getOpenStack().stream().
                map(card -> "|" +format(getCardValue(card) )).forEach(out::print);
        out.println("|");
        out.println(getTableLine(somniumGame.getOpenStack().size()));
    }

    private static void printCardValues(PrintStream out, SomniumPlayer player) {
        for (SomniumCard.CardColor color : SomniumCard.CardColor.values()) {
            out.print("|" + format(Integer.toString(getMax(player.getCards(), color))));
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

    public static void printResults(PrintStream out, SomniumGame somniumGame) {
        printGame(out, somniumGame);
        out.println();
        out.println();
        out.println("--------------------------------------------------------");
        out.println("game over, score");
        somniumGame.getPlayers().stream().
                map(player -> "player " + player.getName() + " has " + player.getScore() + " score").
                forEach(System.out::println);
    }


}
