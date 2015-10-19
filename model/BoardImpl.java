package model;

import java.util.Arrays;

/**
 * {@inheritDoc}
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
        return houses[position % houses.length];
    }

    public void emptyHouse(final int position) {
        houses[position % houses.length] = 0;
    }

    public void addStone(final int position) {
        int stones = houses[position % houses.length];
        stones++;
        houses[position % houses.length] = stones;
    }

    public boolean isCompleted() {
        // When one player no longer has any seeds in any of their houses, the game ends.
        // -- Also ends when mathematically one player has more than half the stones
        // The other player moves all remaining seeds to their stores, and the player with the most seeds in their stores wins.
        final int majority = stonesPerHouse * houses.length / 2;
        if (houses[stores[0]] > majority || houses[stores[1]] > majority) {
            return true;
        }

        int sum = 0;

        for (int i = 0; i < houses.length / 2 - 1; i++) {
            sum += houses[i];
        }
        if (sum == 0) {
            houses[stores[1]] = getTotalStones() - houses[stores[0]];
            return true;
        }

        sum = 0;
        for (int i = houses.length / 2 - 1; i < houses.length; i++) {
            sum += houses[i];
        }

        if (sum == 0) {
            houses[stores[0]] = getTotalStones() - houses[stores[1]];
            return true;
        }

        return false;
    }

    public void grabOppositeStones(final int pos) {
        final int oppositePos = houses.length - 2 - pos % (houses.length - 2);
        final int stones = houses[oppositePos];

        // the opposite house contains seeds
        if (stones > 0) {
            houses[oppositePos] = 0;
            houses[pos] = 0;
            houses[stores[pos / (houses.length / 2)]] += stones + 1;
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
