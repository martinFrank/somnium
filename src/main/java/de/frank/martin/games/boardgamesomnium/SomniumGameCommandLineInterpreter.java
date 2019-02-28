package de.frank.martin.games.boardgamesomnium;

import de.elite.games.cli.CommandLineInterpreter;
import de.elite.games.cli.CommandMapping;
import de.elite.games.cli.DefaultCommandMapping;
import de.frank.martin.games.boardgamesomnium.command.*;

public class SomniumGameCommandLineInterpreter implements CommandLineInterpreter {

    private final SomniumGame somniumGame;
    private final EndTurnCommand endTurnCommand;
    private final StealCommand stealCommand;
    private final ShowCommand showCommand;
    private final RestartCommand restartCommand;
    private final ShowResultCommand showResultCommand;
    private final DrawCommand drawCommand;

    SomniumGameCommandLineInterpreter(SomniumGame somniumGame) {
        super();
        this.somniumGame = somniumGame;
        endTurnCommand = new EndTurnCommand(somniumGame);
        stealCommand = new StealCommand(somniumGame);
        showCommand = new ShowCommand(somniumGame);
        restartCommand = new RestartCommand(somniumGame);
        showResultCommand = new ShowResultCommand(somniumGame);
        drawCommand = new DrawCommand(somniumGame);
    }

    @Override
    public CommandMapping getCommands() {
        final DefaultCommandMapping commandMapping = new DefaultCommandMapping();
        commandMapping.add(showCommand);
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
