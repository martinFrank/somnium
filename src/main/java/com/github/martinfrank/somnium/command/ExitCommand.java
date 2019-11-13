package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumBoard;

import java.util.List;

public class ExitCommand extends Command<SomniumBoard> {

    public ExitCommand(SomniumBoard board) {
        super(board, "exit");
    }

    @Override
    public Response execute(List<String> parameter) {
        getApplication().getCommandInterpreterProvider().getCommandInterpreter().stop();
        return Response.success();
    }
}
