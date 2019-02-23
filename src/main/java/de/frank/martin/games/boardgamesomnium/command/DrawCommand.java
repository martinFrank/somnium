package de.frank.martin.games.boardgamesomnium.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.frank.martin.games.boardgamesomnium.SomniumGame;
import de.frank.martin.games.boardgamesomnium.SomniumGamePrinter;

import java.util.List;

public class DrawCommand extends Command<SomniumGame> {

    public DrawCommand() {
        super("draw");
    }

    @Override
    public Response execute(SomniumGame somniumGame, List<String> list) {
        somniumGame.drawCard();
        SomniumGamePrinter.printGame(System.out, somniumGame);
        return Response.success();
    }
}
