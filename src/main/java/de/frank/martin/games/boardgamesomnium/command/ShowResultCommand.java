package de.frank.martin.games.boardgamesomnium.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.frank.martin.games.boardgamesomnium.SomniumGame;
import de.frank.martin.games.boardgamesomnium.SomniumGamePrinter;

import java.util.List;

public class ShowResultCommand extends Command<SomniumGame> {

    public ShowResultCommand() {
        super("result");
    }

    @Override
    public Response execute(SomniumGame somniumGame, List<String> list) {
        SomniumGamePrinter.printResults(System.out, somniumGame);
        return Response.success();
    }
}
