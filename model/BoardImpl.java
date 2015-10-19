package model;

import java.util.Arrays;

/**
 * Representation of the board, the houses being represented anti-clockwise with a 0-index as te start
 */
public class BoardImpl implements Board {

    private final int[] stores = new int[2];
    private final Integer[] houses;
    private final int stonesPerHouse;

    public BoardImpl(final int playerHouses, final int stones) {

        stonesPerHouse = stones;

        stores[0] = playerHouses;
        stores[1] = (playerHouses + 1) * 2 - 1;

        houses = new Integer[(playerHouses + 1) * 2];

        for (int i = 0; i < houses.length; i++) {
            houses[i] = i != stores[0] && i != stores[1] ? stones : 0;
        }
    }

    public int[] getStores() {
        return stores;
    }

    public Integer getNumberOfHousesPerPlayer() {
        return houses.length / 2 - 1;
    }

    public Integer getTotalStones() {
        return stonesPerHouse * getNumberOfHousesPerPlayer() * 2;
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
        // The other player moves all remaining seeds to their stores, and the player with the most seeds in their stores wins.
        if (houses[stores[0]] > stonesPerHouse * 6 || houses[stores[1]] > stonesPerHouse * 6) {
            return true;
        }

        int sum = 0;

        for (int i = 0; i < 6; i++) {
            sum += houses[i];
        }
        if (sum == 0) {
            houses[stores[1]] = stonesPerHouse * 12 - houses[stores[0]];
            return true;
        }

        sum = 0;
        for (int i = 7; i < 13; i++) {
            sum += houses[i];
        }

        if (sum == 0) {
            houses[stores[0]] = stonesPerHouse * 12 - houses[stores[1]];
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
            houses[stores[pos / 7]] += stones + 1;
        }
    }

    @Override
    public String toString() {
        return "BoardImpl{" +
                "stores=" + Arrays.toString(stores) +
                ", houses=" + Arrays.toString(houses) +
                ", stonesPerHouse=" + stonesPerHouse +
                '}';
    }
}
