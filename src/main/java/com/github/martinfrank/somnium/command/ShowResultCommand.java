package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumGame;
import com.github.martinfrank.somnium.SomniumGamePrinter;

import java.io.PrintStream;
import java.util.List;

public class ShowResultCommand extends Command<SomniumGame> {

    public ShowResultCommand(SomniumGame somniumGame) {
        super(somniumGame, "result");
    }

    @Override
    public Response execute(List<String> list) {
        SomniumGamePrinter.printResults(new PrintStream(System.out), getApplication());
        return Response.success();
    }
}
