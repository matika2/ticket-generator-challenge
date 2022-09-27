package com.lindaropen.ticketgeneratorchallenge.strip.visualmodel;

import com.lindaropen.ticketgeneratorchallenge.exception.visualmodel.InvalidBingoNumberException;
import com.lindaropen.ticketgeneratorchallenge.exception.visualmodel.InvalidStripColumnReferenceException;
import com.lindaropen.ticketgeneratorchallenge.exception.visualmodel.InvalidStripRowReferenceException;

import static com.lindaropen.ticketgeneratorchallenge.strip.Constants.*;

public class VisualBingo {
    private int[][] bingo = new int[SINGLE_BINGO_ROWS][BINGO_COLUMNS];

    public int[] getRow(int rowNumber) {
        validateRowNumber(rowNumber);

        return bingo[rowNumber];
    }

    public void setValue(int row, int column, int value) {
        validateRowNumber(row);
        validateColumnNumber(column);
        validateValue(value);

        bingo[row][column] = value;
    }

    private void validateRowNumber(int rowNumber) {
        if(rowNumber < 0 || rowNumber >= SINGLE_BINGO_ROWS) {
            throw new InvalidStripRowReferenceException();
        }
    }

    private void validateColumnNumber(int columnNumber) {
        if(columnNumber < 0 || columnNumber >= BINGO_COLUMNS) {
            throw new InvalidStripColumnReferenceException();
        }
    }

    private void validateValue(int value) {
        if(value < BINGO_MIN_NUMBER && value != BLANK && value != 0 || value > BINGO_MAX_NUMBER) {
            throw new InvalidBingoNumberException();
        }
    }

    private String rowToString(int[] row) {
        validateColumnNumber(row.length - 1);
        StringBuilder rowString = new StringBuilder();
        rowString.append("|");
        for(int i = 0; i < BINGO_COLUMNS; i++) {
            if(row[i] == BLANK) {
                rowString.append("    |");
            } else if(row[i] < 10 && row[i] > 0 ) {
                rowString.append("  " + row[i] + " |");
            } else {
                rowString.append(" " + row[i] + " |");
            }
        }

        return rowString.toString();
    }

    @Override
    public String toString() {
        StringBuilder rows = new StringBuilder();
        for(int i = 0; i < SINGLE_BINGO_ROWS; i++) {
            rows.append(rowToString(getRow(i)));
            rows.append("\n");
        }

        return rows.toString();
    }
}
