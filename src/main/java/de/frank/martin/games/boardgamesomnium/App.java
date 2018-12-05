package de.frank.martin.games.boardgamesomnium;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static de.frank.martin.games.boardgamesomnium.SomniumCommand.*;

public class App {

    public static void main(String[] args){
        SomniumGame somniumGame = new SomniumGame();
        somniumGame.setup(new SomniumGameSetup());
        SomniumGamePrinter printer = new SomniumGamePrinter(somniumGame);
        somniumGame.initGame();
        while(!somniumGame.allCardsAreDrawn()){
            SomniumPlayer player = somniumGame.getCurrent();
            if (player.isHuman() ){
                handleKeyboardInput(somniumGame, printer);
            }else{
                player.performAiTurn();
            }
        }
        printer.printResults(System.out);

    }

    private static void handleKeyboardInput(SomniumGame somniumGame, SomniumGamePrinter printer) {
        printer.printGame(System.out);
        Scanner scanner = new Scanner(System.in);
        String line;
        while (true){
            List<SomniumCommand> options = somniumGame.getPossibleCommands();
            System.out.println(" possible commands "+options+" for player "+somniumGame.getCurrent().getName());
            line = scanner.nextLine();
            String[] cmd = line.split(" ");
            if (!isValidCommand(cmd[0], options)){
                System.out.println('\''+cmd[0]+"' is not a avalid option "+ options);
                continue;
            }
            if (isCommand(SHOW,cmd[0] )) {
                printer.printGame(System.out);
            }
            if (isCommand(EXIT,cmd[0] )) {
                System.exit(0);
            }
            if (isCommand(DRAW,cmd[0] )) {
                somniumGame.drawCard();
                printer.printGame(System.out);
            }
            if (isCommand(STEAL,cmd[0] )) {
                try {
                    SomniumCard.CardColor color = SomniumCard.CardColor.valueOf(cmd[1]);
                    somniumGame.steal(color);
                }catch (Exception e){
                    System.out.println("you must apply a valid card color, one of "+
                            Arrays.toString(SomniumCard.CardColor.values()));
                }
                printer.printGame(System.out);
            }
            if (isCommand(DONE,cmd[0] )) {
                somniumGame.endPlayersTurn();
                return;
            }
        }
    }
    private static boolean isCommand(SomniumCommand option, String cmd) {
        return option.toString().equalsIgnoreCase(cmd);
    }

    private static boolean isValidCommand(String cmd, List<SomniumCommand> options) {
        return options.stream().anyMatch(e -> isCommand(e, cmd));
    }
}
