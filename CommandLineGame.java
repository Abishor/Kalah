import model.ConsoleRepresentation;
import model.Game;

import java.util.Scanner;

public class CommandLineGame {
    private static final Scanner in = new Scanner(System.in);

    public static void main(final String[] args) {
        final Game game;

        if (args != null && args.length > 0) {
            final int housesPerPlayer = Integer.valueOf(args[0]);
            final int numberOfStones = Integer.valueOf(args[1]);
            game = new Game(housesPerPlayer, numberOfStones);
        } else {
            game = new Game();
        }

        final ConsoleRepresentation consoleRepresentation = new ConsoleRepresentation(game.getBoard());


        while (!game.isFinished()) {
            System.out.println(consoleRepresentation.draw());

            System.out.println("Player " + game.getPlayer() + ", please input move...");
            int position = readStartingPosition();

            while (!game.validChoice(position)) {
                System.out.println("Player " + game.getPlayer() + ", please input move...");
                position = readStartingPosition();
            }
            game.move(position);
        }

        System.out.println(game.getWinner());
    }

    private static int readStartingPosition() {
        int position = -1;
        try {
            position = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please input valid move...");
        }
        return position;
    }
}
