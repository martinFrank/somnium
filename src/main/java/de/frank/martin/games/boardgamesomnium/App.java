package de.frank.martin.games.boardgamesomnium;

import de.elite.games.cli.CommandLineInterpreter;

import java.io.PrintStream;

class App {

    public static void main(String[] args){
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();
        PrintStream printer = new PrintStream(System.out);
        SomniumGamePrinter.printGame(printer, somniumGame);
        CommandLineInterpreter cli = new CommandLineInterpreter(somniumGame, System.in, printer);
        cli.start();
    }

}
