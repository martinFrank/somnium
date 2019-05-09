package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumGame;
import com.github.martinfrank.somnium.SomniumGameSetup;

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
