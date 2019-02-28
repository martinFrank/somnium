package de.frank.martin.games.boardgamesomnium.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.frank.martin.games.boardgamesomnium.SomniumGame;
import de.frank.martin.games.boardgamesomnium.SomniumGamePrinter;

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
        SomniumGamePrinter.printGame(System.out, getApplication());
        return Response.success();
    }
}
