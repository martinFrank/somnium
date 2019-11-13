package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumBoard;

import java.util.List;

public class HelpCommand extends Command<SomniumBoard> {

    public HelpCommand(SomniumBoard board) {
        super(board, "help");
    }

    @Override
    public Response execute(List<String> parameter) {
        System.out.println("heeeeeeelp");
        return Response.success();
    }
}
