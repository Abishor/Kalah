package model;

public class Game {
    private boolean firstPlayerTurn = true;
    private final Board board;

    public Game(final int housesPerPlayer, final int stones) {
        board = new BoardImpl(housesPerPlayer, stones);
    }

    public Game() {
        this(6, 6);
    }

    public void move(final int houseNumber) {
        boolean changeTurn = true;
        final int housesPerPlayer = board.getNumberOfHousesPerPlayer();
        int pos = getIndex(houseNumber, housesPerPlayer);

        int stones = board.getStones(pos);
        // the player removes all seeds from one of the houses under their control
        board.emptyHouse(pos);

        while (stones > 0) {
            pos = (pos + 1) % ((housesPerPlayer + 1) * 2);
            // the player drops one seed in each house in turn, including the player's own store but not their opponent's
            if (firstPlayerTurn && pos == board.getStores()[1] || !firstPlayerTurn && pos == board.getStores()[0]) {
                continue;
            }
            board.addStone(pos);
            stones--;

            // Last stone
            if (stones == 0) {
                // If the last sown seed lands in the player's store, the player gets an additional move
                if (firstPlayerTurn && pos == board.getStores()[0] || !firstPlayerTurn && pos == board.getStores()[1]) {
                    changeTurn = false;
                }

                // If the last sown seed lands in an empty house
                if (board.getStones(pos) == 1) {
                    // owned by the player
                    if (firstPlayerTurn && pos >= 0 && pos < board.getStores()[0] || !firstPlayerTurn && pos > board.getStores()[0]) {
                        board.grabOppositeStones(pos);
                    }
                }
            }
        }

        if (changeTurn) {
            firstPlayerTurn = !firstPlayerTurn;
        }
    }

    private int getIndex(final int houseNumber, final int housesPerPlayer) {
        int pos = houseNumber % (housesPerPlayer + 1);
        return firstPlayerTurn ? pos - 1 : housesPerPlayer * 2 + 1 - pos;
    }

    public boolean validChoice(final int position) {
        if (!(position > 0 && position <= board.getNumberOfHousesPerPlayer())) {
            return false;
        } else {
            if (board.getStones(getIndex(position, board.getNumberOfHousesPerPlayer())) == 0) {
                System.out.println("You have nothing to move in " + position);
                return false;
            }
        }
        return true;
    }

    public int getPlayer() {
        return firstPlayerTurn ? 1 : 2;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isFinished() {
        return board.isCompleted();
    }

    public String getWinner() {
        final int diff = board.getStones(board.getStores()[0]) - board.getStones(board.getStores()[1]);
        if (diff == 0) {
            return "DRAW!";
        }
        return diff > 0 ? "Player 1 wins!" : "Player 2 wins!";
    }
}
