package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumCard;
import com.github.martinfrank.somnium.SomniumGame;

import java.util.List;
import java.util.Optional;

public class StealCommand extends Command<SomniumGame> {


    public StealCommand(SomniumGame somniumGame) {
        super(somniumGame, "steal");
    }

    public StealCommand() {
        this(null);
    }


    @Override
    public Response execute(List<String> list) {
        try {
            SomniumGame somniumGame = getApplication();
            SomniumCard.CardColor color = SomniumCard.CardColor.valueOf(list.get(0));
            Optional<SomniumCard> card = somniumGame.getVictim().getBestCard(color);
            card.ifPresent(somniumGame::steal);
            ShowCommand.printGame(getApplication());
            return Response.success();
        } catch (RuntimeException e) {
            return Response.fail(e.toString());
        }
    }
}