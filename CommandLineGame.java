import model.Game;
import model.Representation;

import java.util.Scanner;

public class CommandLineGame {
    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final Game game;

        if (args != null && args.length > 0) {
            final int housesPerPlayer = Integer.valueOf(args[0]);
            final int numberOfStones = Integer.valueOf(args[1]);
            game = new Game(housesPerPlayer, numberOfStones);
        } else {
            game = new Game();
        }

        final Representation representation = new Representation(game.getBoard());

        while (!game.isFinished()) {
            int position = -1;
            System.out.println(representation.draw());
            System.out.println("Player " + game.getPlayer() + ", please input move...");

            while (!(position > 0 && position < 7)) {
                try {
                    position = Integer.parseInt(in.nextLine());
                } catch (NumberFormatException e) {
                    position = -1;
                    System.out.println("Player " + game.getPlayer() + ", please input valid move (1 to 6)...");
                }
            }
            game.move(position);
        }

        System.out.println(game.getWinner());
    }
}
