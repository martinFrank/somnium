package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumGame;

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
