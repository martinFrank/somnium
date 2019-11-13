package com.github.martinfrank.somnium.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.somnium.SomniumBoard;
import com.github.martinfrank.somnium.SomniumCard;

import java.util.List;
import java.util.Optional;

public class StealCommand extends Command<SomniumBoard> {


    public StealCommand(SomniumBoard board) {
        super(board, "steal");
    }

    public StealCommand() {
        this(null);
    }


    @Override
    public Response execute(List<String> list) {
        try {
            SomniumBoard board = getApplication();
            SomniumCard.CardColor color = SomniumCard.CardColor.valueOf(list.get(0));
            Optional<SomniumCard> card = board.getVictim().getBestCard(color);
            card.ifPresent(board::steal);
            ShowCommand.printGame(board);
            return Response.success();
        } catch (RuntimeException e) {
            return Response.fail(e.toString());
        }
    }
}
