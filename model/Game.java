package model;

public class Game {
    private boolean firstPlayerTurn = true;
    private final BoardImpl board;

    public Game(final int housesPerPlayer, final int stones) {
        board = new BoardImpl(housesPerPlayer, stones);
    }

    public Game() {
        this(6, 6);
    }

    public void move(final int houseNumber) {
        boolean changeTurn = true;

        int pos = houseNumber % 7;
        pos = firstPlayerTurn ? pos - 1 : 13 - pos;

        int stones = board.getStones(pos);
        // the player removes all seeds from one of the houses under their control
        board.emptyHouse(pos);

        while (stones > 0) {
            pos = (pos + 1) % 14;
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

    public int getPlayer() {
        return firstPlayerTurn ? 1 : 2;
    }

    public BoardImpl getBoard() {
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
