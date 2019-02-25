package de.frank.martin.games.boardgamesomnium;

import de.elite.games.cli.BaseCommandLineInterpreter;
import de.elite.games.cli.Command;
import de.frank.martin.games.boardgamesomnium.command.*;

import java.util.HashSet;
import java.util.Set;

public class SomniumGameCommandLineInterpreter extends BaseCommandLineInterpreter<SomniumGame> {

    SomniumGameCommandLineInterpreter(SomniumGame somniumGame) {
        super(somniumGame);
    }

    @Override
    public Set<Command<SomniumGame>> getCommands() {
        Set<Command<SomniumGame>> commands = new HashSet<>();
        commands.add(new ShowCommand());
        if (getApplication().isTurnFailed()) {
            commands.add(new EndTurnCommand());
            return commands;
        }
        if (getApplication().isThiefOpen()) {
            commands.add(new StealCommand());
            return commands;
        }
        if (getApplication().isGameOver()) {
            commands.add(new RestartCommand());
            commands.add(new ShowResultCommand());
        } else {
            if (getApplication().hasCardsToDraw()) {
                commands.add(new DrawCommand());
            }
            if (getApplication().getOpenStack().size() > 0) {
                commands.add(new EndTurnCommand());
            }
        }
        return commands;
    }
}
