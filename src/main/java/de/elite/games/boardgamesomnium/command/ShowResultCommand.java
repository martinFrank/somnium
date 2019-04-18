package de.elite.games.boardgamesomnium.command;

import de.elite.games.boardgamesomnium.SomniumGame;
import de.elite.games.boardgamesomnium.SomniumGamePrinter;
import de.elite.games.cli.Command;
import de.elite.games.cli.Response;

import java.io.PrintStream;
import java.util.List;

public class ShowResultCommand extends Command<SomniumGame> {

    public ShowResultCommand(SomniumGame somniumGame) {
        super(somniumGame, "result");
    }

    @Override
    public Response execute(List<String> list) {
        SomniumGamePrinter.printResults(new PrintStream(System.out), getApplication());
        return Response.success();
    }
}
