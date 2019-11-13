package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumBoard;
import com.github.martinfrank.somnium.SomniumGameSetup;

import java.util.List;

public class RestartCommand extends Command<SomniumBoard> {

    public RestartCommand(SomniumBoard board) {
        super(board, "restart");
    }

    @Override
    public Response execute(List<String> list) {
        SomniumBoard board = getApplication();
        board.setup(new SomniumGameSetup());
        board.initGame();
        ShowCommand.printGame(getApplication());
        return Response.success();
    }
}
