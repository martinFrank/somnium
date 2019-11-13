package com.github.martinfrank.somnium;

class App {

    public static void main(String[] args){
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();
//        PrintStream printer = new PrintStream(System.out);//NOSONAR - it's a console app
//        SomniumGamePrinter.printGame(printer, somniumGame);
//        CommandLineInterpreter cli = new CommandLineInterpreter(somniumGame, System.in, printer);
//        cli.start();
        somniumGame.getCommandInterpreter().start();
    }


}
