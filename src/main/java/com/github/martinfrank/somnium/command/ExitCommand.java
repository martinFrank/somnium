package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumGame;

import java.util.List;

public class ExitCommand extends Command<SomniumGame> {

    public ExitCommand(SomniumGame application) {
        super(application, "exit");
    }

    @Override
    public Response execute(List<String> parameter) {
        getApplication().getCommandInterpreter().stop();
        return Response.success();
    }
}
