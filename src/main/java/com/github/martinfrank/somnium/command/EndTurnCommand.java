package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumBoard;
import com.github.martinfrank.somnium.SomniumPlayer;

import java.util.List;

public class EndTurnCommand extends Command<SomniumBoard> {

    public EndTurnCommand(SomniumBoard board) {
        super(board, "done");
    }

    @Override
    public Response execute(List<String> list) {
        SomniumBoard board = getApplication();
        board.endPlayersTurn();
        if (!board.allCardsAreDrawn()) {
            SomniumPlayer player = board.getCurrentPlayer();
            if (!player.isHuman()) {
                player.performAiTurn();
                ShowCommand.printGame(getApplication());
            }

        }

        return Response.success();
    }
}
