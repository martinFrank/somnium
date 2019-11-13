package com.github.martinfrank.somnium;

import com.github.martinfrank.cli.CommandList;
import com.github.martinfrank.cli.CommandProvider;
import com.github.martinfrank.cli.DefaultCommandList;
import com.github.martinfrank.somnium.command.*;

public class SomniumGameCommandProvider implements CommandProvider {

    private final SomniumBoard board;
    private final EndTurnCommand endTurnCommand;
    private final StealCommand stealCommand;
    private final ShowCommand showCommand;
    private final RestartCommand restartCommand;
    private final ShowResultCommand showResultCommand;
    private final DrawCommand drawCommand;
    private final HelpCommand helpCommand;
    private final ExitCommand exitCommand;

    SomniumGameCommandProvider(SomniumBoard board) {
        super();
        this.board = board;
        endTurnCommand = new EndTurnCommand(board);
        stealCommand = new StealCommand(board);
        showCommand = new ShowCommand(board);
        restartCommand = new RestartCommand(board);
        showResultCommand = new ShowResultCommand(board);
        drawCommand = new DrawCommand(board);
        helpCommand = new HelpCommand(board);
        exitCommand = new ExitCommand(board);
    }

    @Override
    public CommandList getCommands() {
        final DefaultCommandList commandMapping = new DefaultCommandList();
        commandMapping.add(showCommand);
        commandMapping.add(helpCommand);
        commandMapping.add(exitCommand);
        if (board.isTurnFailed()) {
            commandMapping.add(endTurnCommand);
            return commandMapping;
        }
        if (board.isThiefOpen()) {
            commandMapping.add(stealCommand);
            return commandMapping;
        }
        if (board.isGameOver()) {
            commandMapping.add(restartCommand);
            commandMapping.add(showResultCommand);
        } else {
            if (board.hasCardsToDraw()) {
                commandMapping.add(drawCommand);
            }
            if (board.hasCardBeenDrawn()) {
                commandMapping.add(endTurnCommand);
            }
        }
        return commandMapping;
    }
}
