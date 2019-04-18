package de.elite.games.boardgamesomnium.command;

import de.elite.games.boardgamesomnium.SomniumGame;
import de.elite.games.boardgamesomnium.SomniumGamePrinter;
import de.elite.games.cli.Command;
import de.elite.games.cli.Response;

import java.io.PrintStream;
import java.util.List;

public class ShowCommand extends Command<SomniumGame> {

    public ShowCommand(SomniumGame somniumGame) {
        super(somniumGame, "show");
    }

    static void printGame(SomniumGame somniumGame) {
        SomniumGamePrinter.printGame(new PrintStream(System.out), somniumGame);
    }

    @Override
    public Response execute(List<String> list) {
        printGame(getApplication());
        return Response.success();
    }
}
