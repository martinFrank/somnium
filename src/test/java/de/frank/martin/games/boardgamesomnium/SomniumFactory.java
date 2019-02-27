package de.frank.martin.games.boardgamesomnium;

public class SomniumFactory {

    SomniumFactory() {

    }

    SomniumGame create() {
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new SomniumGameSetup());
        somniumGame.initGame();
        return somniumGame;
    }
}
