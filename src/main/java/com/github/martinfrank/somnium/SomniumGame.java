package com.github.martinfrank.somnium;

import com.github.martinfrank.cli.CommandInterpreter;
import com.github.martinfrank.cli.CommandInterpreterProvider;
import com.github.martinfrank.cli.CommandList;
import com.github.martinfrank.cli.CommandProvider;

public class SomniumGame implements CommandProvider, CommandInterpreterProvider {


    private final SomniumGameCommandProvider somniumGameCommandProvider;
    private final CommandInterpreter commandInterpreter;
    private SomniumBoard board;

    SomniumGame() {
        board = new SomniumBoard(this);
        somniumGameCommandProvider = new SomniumGameCommandProvider(board);
        commandInterpreter = new CommandInterpreter(somniumGameCommandProvider);

        board.setup(new SomniumGameSetup());
        board.initGame();
    }

    public SomniumBoard getBoard() {
        return board;
    }

    @Override
    public CommandList getCommands() {
        return somniumGameCommandProvider.getCommands();
    }

    @Override
    public CommandInterpreter getCommandInterpreter() {
        return commandInterpreter;
    }
}
