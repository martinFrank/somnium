package de.elite.games.boardgamesomnium.command;

import de.elite.games.boardgamesomnium.SomniumGame;
import de.elite.games.boardgamesomnium.SomniumPlayer;
import de.elite.games.cli.Command;
import de.elite.games.cli.Response;

import java.util.List;

public class EndTurnCommand extends Command<SomniumGame> {

    public EndTurnCommand(SomniumGame somniumGame) {
        super(somniumGame, "done");
    }

    @Override
    public Response execute(List<String> list) {
        SomniumGame somniumGame = getApplication();
        somniumGame.endPlayersTurn();
        if (!somniumGame.allCardsAreDrawn()) {
            SomniumPlayer player = somniumGame.getCurrentPlayer();
            if (!player.isHuman()) {
                player.performAiTurn();
                ShowCommand.printGame(getApplication());
            }

        }

        return Response.success();
    }
}
