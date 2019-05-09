package com.github.martinfrank.somnium;

import com.github.martinfrank.cli.CommandLineInterpreter;

import java.io.PrintStream;

class App {

    public static void main(String[] args){
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();
        PrintStream printer = new PrintStream(System.out);//NOSONAR - it's a console app
        SomniumGamePrinter.printGame(printer, somniumGame);
        CommandLineInterpreter cli = new CommandLineInterpreter(somniumGame, System.in, printer);
        cli.start();
    }

}
