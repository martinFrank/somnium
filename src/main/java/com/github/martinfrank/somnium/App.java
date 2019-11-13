package com.github.martinfrank.somnium;

class App {

    public static void main(String[] args){
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.getCommandInterpreter().start();
    }

}
