package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumGame;
import com.github.martinfrank.somnium.SomniumGamePrinter;

import java.io.PrintStream;
import java.util.List;

public class ShowCommand extends Command<SomniumGame> {

    public ShowCommand(SomniumGame somniumGame) {
        super(somniumGame, "show");
    }

    static void printGame(SomniumGame somniumGame) {
        SomniumGamePrinter.printGame(new PrintStream(System.out), somniumGame);//NOSONAR - it's a console app
    }

    @Override
    public Response execute(List<String> list) {
        printGame(getApplication());
        return Response.success();
    }
}
