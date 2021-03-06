package com.github.martinfrank.somnium;

import com.github.martinfrank.boardgamelib.BoardGameSetup;

import java.util.ArrayList;
import java.util.List;

public class SomniumFactory {

    SomniumFactory() {

    }

    SomniumGame create() {
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.getBoard().setup(getTestSetup());
        somniumGame.getBoard().initGame();
        return somniumGame;
    }

    private BoardGameSetup<SomniumPlayer> getTestSetup() {

        return new BoardGameSetup<SomniumPlayer>() {

            @Override
            public List<SomniumPlayer> getPlayers() {
                ArrayList<SomniumPlayer> player = new ArrayList<>();
                player.add(new SomniumPlayer("YOU", 0xFFFF00, true));
                player.add(new SomniumPlayer("CPU", 0x0000FF, true));
                return player;
            }

            @Override
            public int getMaximumRounds() {
                return 0;
            }
        };
    }
}
