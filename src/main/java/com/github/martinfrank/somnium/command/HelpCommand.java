package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumGame;

import java.util.List;

public class HelpCommand extends Command<SomniumGame> {

    public HelpCommand(SomniumGame application) {
        super(application, "help");
    }

    @Override
    public Response execute(List<String> parameter) {
        System.out.println("heeeeeeelp");
        return Response.success();
    }
}
