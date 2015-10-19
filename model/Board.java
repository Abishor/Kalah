package model;

/**
 * ConsoleRepresentation of the board, the houses being represented anti-clockwise with a 0-index as the start
 */
public interface Board {
    Integer getNumberOfHousesPerPlayer();

    Integer getTotalStones();

    Integer getStones(final int position);

    int[] getStores();

    void emptyHouse(final int position);

    void addStone(final int position);

    boolean isCompleted();

    void grabOppositeStones(final int pos);
}
