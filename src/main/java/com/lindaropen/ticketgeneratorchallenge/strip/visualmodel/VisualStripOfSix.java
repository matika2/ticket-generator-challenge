package com.lindaropen.ticketgeneratorchallenge.strip.visualmodel;

import static com.lindaropen.ticketgeneratorchallenge.strip.Constants.*;
import static com.lindaropen.ticketgeneratorchallenge.strip.validation.InputValidation.validateStripOfSixColumns;

/**
 *
 */
public class VisualStripOfSix {
    private VisualBingo[] bingos = new VisualBingo[STRIP_OF_SIX_BINGO_COUNT];

    public VisualStripOfSix() {
        for(int i = 0; i < STRIP_OF_SIX_BINGO_COUNT; i++) {
            bingos[i] = new VisualBingo();
        }
    }

    public void buildFromColumns(int[][] stripOfSixColumns) {
        validateStripOfSixColumns(stripOfSixColumns);

        for(int column = 0; column < BINGO_COLUMNS; column++) {
            for(int row = 0; row < STRIP_OF_SIX_TOTAL_ROWS; row++) {
                bingos[row/ SINGLE_BINGO_ROWS].setValue(row % SINGLE_BINGO_ROWS, column, stripOfSixColumns[column][row]);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < STRIP_OF_SIX_BINGO_COUNT; i++) {
            builder.append(bingos[i].toString());
            builder.append("\n");
        }

        return builder.toString();
    }
}
