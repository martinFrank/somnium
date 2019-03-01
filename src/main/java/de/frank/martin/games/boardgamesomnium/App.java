package de.frank.martin.games.boardgamesomnium;

import de.elite.games.cli.CommandLineInterpreter;

class App {

    public static void main(String[] args){
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();
        SomniumGamePrinter.printGame(System.out, somniumGame);
        CommandLineInterpreter cli = new CommandLineInterpreter(somniumGame, System.in, System.out);
        cli.start();
    }

}
