package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumGame;
import com.github.martinfrank.somnium.SomniumPlayer;

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
