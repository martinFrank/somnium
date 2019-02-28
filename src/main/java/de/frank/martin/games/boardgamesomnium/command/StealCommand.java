package de.frank.martin.games.boardgamesomnium.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.frank.martin.games.boardgamesomnium.SomniumCard;
import de.frank.martin.games.boardgamesomnium.SomniumGame;
import de.frank.martin.games.boardgamesomnium.SomniumGamePrinter;

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
            SomniumGamePrinter.printGame(System.out, somniumGame);
            return Response.success();
        } catch (RuntimeException e) {
            return Response.fail(e.toString());
        }
    }
}
