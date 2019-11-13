package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumBoard;
import com.github.martinfrank.somnium.SomniumGamePrinter;

import java.io.PrintStream;
import java.util.List;

public class ShowResultCommand extends Command<SomniumBoard> {

    public ShowResultCommand(SomniumBoard board) {
        super(board, "result");
    }

    @Override
    public Response execute(List<String> list) {
        SomniumGamePrinter.printResults(new PrintStream(System.out), getApplication());//NOSONAR - it's a console app
        return Response.success();
    }
}
