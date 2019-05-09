package com.github.martinfrank.somnium;

import com.github.martinfrank.boardgamelib.BoardGameSetup;

import java.util.ArrayList;
import java.util.List;

public class SomniumGameSetup implements BoardGameSetup<SomniumPlayer>  {

    @Override
    public List<SomniumPlayer> getPlayers() {
        ArrayList<SomniumPlayer> player = new ArrayList<>();
        player.add(new SomniumPlayer("YOU", 0xFFFF00, true));
        player.add(new SomniumPlayer("CPU", 0x0000FF, false));
        return player;
    }

    @Override
    public int getMaximumRounds() {
        return 0;
    }
}
