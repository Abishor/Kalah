package model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang.StringUtils.leftPad;

public class ConsoleRepresentation {
    private final Board board;
    private final int digits, housesPerPlayer;

    public ConsoleRepresentation(final Board board) {
        checkNotNull(board, "A board must be specified");
        this.board = board;
        digits = board.getTotalStones().toString().length();
        housesPerPlayer = board.getNumberOfHousesPerPlayer();
    }

    public String draw() {
        final String columnNames = getFillerText(board.getNumberOfHousesPerPlayer());
        return columnNames + getSecondPlayerHouses() + getMiddleRow() + getFirstPlayerHouses() + columnNames;
    }

    private String getFillerText(final int elements) {
        final StringBuilder result = new StringBuilder(emptyCell());
        for (int i = 1; i <= elements; i++) {
            if (i == 1) {
                result.append(pad(i));
            } else {
                result.append(" ").append(pad(i));
            }
        }
        result.append(emptyCell()).append("\n");
        return result.toString();
    }

    private String getFirstPlayerHouses() {
        final StringBuilder result = new StringBuilder(emptyCell());
        for (int i = 0; i < housesPerPlayer; i++) {
            result.append(pad(board.getStones(i))).append("|");
        }
        result.append(leftPad("", digits, ' ')).append("|").append("\n");
        return result.toString();
    }

    private String getSecondPlayerHouses() {
        final StringBuilder result = new StringBuilder(emptyCell());
        for (int i = housesPerPlayer * 2; i > housesPerPlayer; i--) {
            result.append(pad(board.getStones(i))).append("|");
        }
        result.append(leftPad("", digits, ' ')).append("|").append("\n");
        return result.toString();
    }

    private String getMiddleRow() {
        return "|" + pad(board.getStones(board.getStores()[1])) + "|"
                + leftPad("", housesPerPlayer * (digits + 1) - 1, "-") +
                "|" + pad(board.getStones(board.getStores()[0])) + "|\n";
    }

    private String emptyCell() {
        return "|" + leftPad("", digits, ' ') + "|";
    }

    private String pad(final int content) {
        final String contentRepresentation = String.valueOf(content);
        final int length = contentRepresentation.length();

        checkArgument(length <= digits, "Content does not fit the padding size");
        return leftPad(contentRepresentation, digits, " ");
    }
}
