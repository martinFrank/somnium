package de.frank.martin.games.boardgamesomnium.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.frank.martin.games.boardgamesomnium.SomniumGame;
import de.frank.martin.games.boardgamesomnium.SomniumGameSetup;

import java.util.List;

public class RestartCommand extends Command<SomniumGame> {

    public RestartCommand(SomniumGame somniumGame) {
        super(somniumGame, "restart");
    }

    @Override
    public Response execute(List<String> list) {
        SomniumGame somniumGame = getApplication();
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();
        ShowCommand.printGame(getApplication());
        return Response.success();
    }
}
