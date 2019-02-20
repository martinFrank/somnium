package de.frank.martin.games.boardgamesomnium.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.frank.martin.games.boardgamesomnium.SomniumGame;
import de.frank.martin.games.boardgamesomnium.SomniumGamePrinter;
import de.frank.martin.games.boardgamesomnium.SomniumPlayer;

import java.util.List;

public class EndTurnCommand extends Command<SomniumGame> {

    public EndTurnCommand() {
        super("done");
    }

    @Override
    public Response execute(SomniumGame somniumGame, List<String> list) {
        somniumGame.endPlayersTurn();
        if (!somniumGame.allCardsAreDrawn()) {
            SomniumPlayer player = somniumGame.getCurrent();
            player.performAiTurn();
            SomniumGamePrinter.printGame(System.out, somniumGame);
        }

        return Response.success();
    }
}
