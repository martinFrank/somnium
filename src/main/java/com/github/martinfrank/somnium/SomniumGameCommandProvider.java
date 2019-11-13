package com.github.martinfrank.somnium;

import com.github.martinfrank.cli.CommandList;
import com.github.martinfrank.cli.CommandProvider;
import com.github.martinfrank.cli.DefaultCommandList;
import com.github.martinfrank.somnium.command.*;

public class SomniumGameCommandProvider implements CommandProvider {

    private final SomniumGame somniumGame;
    private final EndTurnCommand endTurnCommand;
    private final StealCommand stealCommand;
    private final ShowCommand showCommand;
    private final RestartCommand restartCommand;
    private final ShowResultCommand showResultCommand;
    private final DrawCommand drawCommand;
    private final HelpCommand helpCommand;
    private final ExitCommand exitCommand;

    SomniumGameCommandProvider(SomniumGame somniumGame) {
        super();
        this.somniumGame = somniumGame;
        endTurnCommand = new EndTurnCommand(somniumGame);
        stealCommand = new StealCommand(somniumGame);
        showCommand = new ShowCommand(somniumGame);
        restartCommand = new RestartCommand(somniumGame);
        showResultCommand = new ShowResultCommand(somniumGame);
        drawCommand = new DrawCommand(somniumGame);
        helpCommand = new HelpCommand(somniumGame);
        exitCommand = new ExitCommand(somniumGame);
    }

    @Override
    public CommandList getCommands() {
        final DefaultCommandList commandMapping = new DefaultCommandList();
        commandMapping.add(showCommand);
        commandMapping.add(helpCommand);
        commandMapping.add(exitCommand);
        if (somniumGame.isTurnFailed()) {
            commandMapping.add(endTurnCommand);
            return commandMapping;
        }
        if (somniumGame.isThiefOpen()) {
            commandMapping.add(stealCommand);
            return commandMapping;
        }
        if (somniumGame.isGameOver()) {
            commandMapping.add(restartCommand);
            commandMapping.add(showResultCommand);
        } else {
            if (somniumGame.hasCardsToDraw()) {
                commandMapping.add(drawCommand);
            }
            if (somniumGame.hasCardBeenDrawn()) {
                commandMapping.add(endTurnCommand);
            }
        }
        return commandMapping;
    }
}
