package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumBoard;
import com.github.martinfrank.somnium.SomniumGamePrinter;

import java.io.PrintStream;
import java.util.List;

public class ShowCommand extends Command<SomniumBoard> {

    public ShowCommand(SomniumBoard board) {
        super(board, "show");
    }

    static void printGame(SomniumBoard board) {
        SomniumGamePrinter.printGame(new PrintStream(System.out), board);//NOSONAR - it's a console app
    }

    @Override
    public Response execute(List<String> list) {
        printGame(getApplication());
        return Response.success();
    }
}
