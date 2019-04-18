package de.elite.games.boardgamesomnium.command;

import de.elite.games.boardgamesomnium.SomniumGame;
import de.elite.games.cli.Command;
import de.elite.games.cli.Response;

import java.util.List;

public class DrawCommand extends Command<SomniumGame> {

    public DrawCommand(SomniumGame somniumGame) {
        super(somniumGame, "draw");
    }

    public DrawCommand() {
        this(null);
    }

    @Override
    public Response execute(List<String> list) {
        getApplication().drawCard();
        ShowCommand.printGame(getApplication());
        return Response.success();
    }
}
