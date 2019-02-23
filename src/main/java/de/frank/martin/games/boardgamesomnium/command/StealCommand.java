package de.frank.martin.games.boardgamesomnium.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.frank.martin.games.boardgamesomnium.SomniumCard;
import de.frank.martin.games.boardgamesomnium.SomniumGame;
import de.frank.martin.games.boardgamesomnium.SomniumGamePrinter;

import java.util.List;

public class StealCommand extends Command<SomniumGame> {



    public StealCommand() {
        super("steal");
    }


    @Override
    public Response execute(SomniumGame somniumGame, List<String> list) {
        try {
            SomniumCard.CardColor color = SomniumCard.CardColor.valueOf(list.get(0));
            somniumGame.steal(color);
            SomniumGamePrinter.printGame(System.out, somniumGame);
            return Response.success();
        } catch (RuntimeException e) {
            return Response.fail(e.toString());
        }
    }
}
