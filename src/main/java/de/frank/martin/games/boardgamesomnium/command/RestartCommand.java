package de.frank.martin.games.boardgamesomnium.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.frank.martin.games.boardgamesomnium.SomniumGame;
import de.frank.martin.games.boardgamesomnium.SomniumGamePrinter;
import de.frank.martin.games.boardgamesomnium.SomniumGameSetup;

import java.util.List;

public class RestartCommand extends Command<SomniumGame> {

    public RestartCommand() {
        super("restart");
    }

    @Override
    public Response execute(SomniumGame somniumGame, List<String> list) {
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();
        SomniumGamePrinter.printGame(System.out, somniumGame);
        return Response.success();
    }
}
