package model;

/**
 * Representation of the board, the houses being represented anti-clockwise with a 0-index as te start
 */
public class Board {
    private static final String filler = "     1  2  3  4  5  6    \n";
    public static final int[] STORE = new int[]{6, 13};
    private final Integer[] houses = new Integer[14];
    private final int initStones;

    public Board(final int stones) {

        initStones = stones;
        for (int i = 0; i < houses.length; i++) {
            if (i != STORE[0] && i != STORE[1]) {
                houses[i] = stones;
            } else {
                houses[i] = 0;
            }
        }
    }

    public Integer getStones(final int position) {
        return houses[position % 14];
    }

    public void emptyHouse(final int position) {
        houses[position % 14] = 0;
    }

    public void addStone(final int position) {
        int stones = houses[position % 14];
        stones++;
        houses[position % 14] = stones;
    }

    public boolean isCompleted() {
        // When one player no longer has any seeds in any of their houses, the game ends.
        // -- Also ends when mathematically one player has more than half the stones
        // The other player moves all remaining seeds to their store, and the player with the most seeds in their store wins.
        if (houses[STORE[0]] > initStones * 6 || houses[STORE[1]] > initStones * 6) {
            return true;
        }

        int sum = 0;

        for (int i = 0; i < 6; i++) {
            sum += houses[i];
        }
        if (sum == 0) {
            houses[STORE[1]] = initStones * 12 - houses[STORE[0]];
            return true;
        }

        sum = 0;
        for (int i = 7; i < 13; i++) {
            sum += houses[i];
        }

        if (sum == 0) {
            houses[STORE[0]] = initStones * 12 - houses[STORE[1]];
            return true;
        }

        return false;
    }

    public void grabOppositeStones(final int pos) {
        final int oppositePos = 12 - pos % 12;
        final int stones = houses[oppositePos];

        // the opposite house contains seeds
        if (stones > 0) {
            houses[oppositePos] = 0;
            houses[pos] = 0;
            houses[STORE[pos / 7]] += stones + 1;
        }
    }


    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(filler + "\n|  |");

        for (int i = 12; i > 6; i--) {
            result.append(pad(houses[i])).append("|");
        }

        result.append("  |\n|").append(pad(houses[13])).append("|-----------------|").append(pad(houses[6])).append("|\n|  ");

        for (int i = 0; i < 6; i++) {
            result.append("|").append(pad(houses[i]));
        }
        result.append("|  |\n").append(filler);

        return result.toString();
    }

    private String pad(final Integer house) {
        return String.format("%1$2s", house);
    }
}
