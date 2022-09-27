package com.lindaropen.ticketgeneratorchallenge.strip.manipulation;

import org.junit.jupiter.api.Test;

import static com.lindaropen.ticketgeneratorchallenge.strip.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing strip of six model conversion methods.
 */
class StripModelConverterTest {
    public static final int[][] validStripOfSixColumns = {
            {0,BLANK,BLANK,BLANK,0,BLANK,0,0,0,0,BLANK,BLANK,0,0,BLANK,0,BLANK,BLANK},
            {0,BLANK,0,BLANK,0,0,0,BLANK,BLANK,0,BLANK,0,BLANK,0,BLANK,BLANK,0,0},
            {BLANK,BLANK,0,BLANK,BLANK,0,BLANK,BLANK,0,BLANK,0,0,0,0,0,0,0,BLANK},
            {BLANK,0,0,0,0,0,BLANK,BLANK,0,BLANK,0,BLANK,0,BLANK,0,BLANK,BLANK,0},
            {0,0,BLANK,0,0,BLANK,0,0,BLANK,BLANK,BLANK,0,0,BLANK,0,BLANK,0,BLANK},
            {0,0,0,0,BLANK,BLANK,BLANK,0,0,0,BLANK,0,BLANK,0,BLANK,0,BLANK,BLANK},
            {0,BLANK,BLANK,BLANK,0,0,0,BLANK,0,BLANK,0,BLANK,0,BLANK,BLANK,0,0,0},
            {BLANK,0,BLANK,0,BLANK,BLANK,0,0,BLANK,0,0,BLANK,BLANK,0,0,0,BLANK,0},
            {BLANK,0,0,0,BLANK,0,BLANK,0,BLANK,0,0,0,BLANK,BLANK,0,BLANK,0,0},
    };

    public static final int[][] validStripOfSixRows = {
            {0,0,BLANK,BLANK,0,0,0,BLANK,BLANK},
            {BLANK,BLANK,BLANK,0,0,0,BLANK,0,0},
            {BLANK,0,0,0,BLANK,0,BLANK,BLANK,0},
            {BLANK,BLANK,BLANK,0,0,0,BLANK,0,0},
            {0,0,BLANK,0,0,BLANK,0,BLANK,BLANK},
            {BLANK,0,0,0,BLANK,BLANK,0,BLANK,BLANK},
            {0,0,BLANK,BLANK,0,BLANK,0,0,BLANK},
            {0,BLANK,BLANK,BLANK,0,0,BLANK,0,0},
            {0,BLANK,0,0,BLANK,0,0,BLANK,BLANK},
            {0,0,BLANK,BLANK,BLANK,0,BLANK,0,0},
            {BLANK,BLANK,0,0,BLANK,BLANK,0,0,0},
            {BLANK,0,0,BLANK,0,0,BLANK,BLANK,0},
            {0,BLANK,0,0,0,BLANK,0,BLANK,BLANK},
            {0,0,0,BLANK,BLANK,0,BLANK,0,BLANK},
            {BLANK,BLANK,0,0,0,BLANK,BLANK,0,0},
            {0,BLANK,0,BLANK,BLANK,0,0,0,BLANK},
            {BLANK,0,0,BLANK,0,BLANK,0,BLANK,0},
            {BLANK,0,BLANK,0,BLANK,BLANK,0,0,0}
    };

    @Test
    void testStripOfSixColumnsToRows() {
        fillWithNumbers(validStripOfSixColumns);
        int[][] stripOfSixRows = StripModelConverter.stripOfSixColumnsToRows(validStripOfSixColumns);

        assertEquals(STRIP_OF_SIX_TOTAL_ROWS, stripOfSixRows.length);

        for(int row = 0; row < stripOfSixRows.length; row++) {
            assertEquals(BINGO_COLUMNS, stripOfSixRows[row].length);
            for(int column = 0; column < stripOfSixRows[row].length; column++) {
                assertEquals(stripOfSixRows[row][column], validStripOfSixColumns[column][row]);
            }
        }
    }

    @Test
    void testStripOfSixRowsToColumns() {
        fillWithNumbers(validStripOfSixRows);
        int[][] stripOfSixColumns = StripModelConverter.stripOfSixRowsToColumns(validStripOfSixRows);

        assertEquals(BINGO_COLUMNS, stripOfSixColumns.length);

        for(int column = 0; column < stripOfSixColumns.length; column++) {
            assertEquals(STRIP_OF_SIX_TOTAL_ROWS, stripOfSixColumns[column].length);
            for(int row = 0; row < stripOfSixColumns[column].length; row++) {
                assertEquals(stripOfSixColumns[column][row], validStripOfSixRows[row][column]);
            }
        }
    }

    @Test
    void testStripOfSixRowsToStrips() {
        fillWithNumbers(validStripOfSixRows);
        int[][] stripOfSix = StripModelConverter.stripOfSixRowsToStrips(validStripOfSixRows);

        assertEquals(6, stripOfSix.length);

        for(int strip = 0; strip < 6; strip++) {
            assertEquals(27, stripOfSix[strip].length);
            for(int stripPosition = 0; stripPosition < 27; stripPosition++) {
                assertEquals(stripOfSix[strip][stripPosition], validStripOfSixRows[strip * 3 + stripPosition / 9][stripPosition % 9]);
            }
        }
    }

    @Test
    void stripOfSixToRowsTest() {
        int[][] stripOfSix = getAnInitialValidStripOfSix();
        fillWithNumbers(stripOfSix);
        int[][] stripOfSixRows = StripModelConverter.stripOfSixToRows(stripOfSix);

        assertEquals(STRIP_OF_SIX_TOTAL_ROWS, stripOfSixRows.length);

        for(int row = 0; row < stripOfSixRows.length; row++) {
            assertEquals(BINGO_COLUMNS, stripOfSixRows[row].length);
            for(int column = 0; column < stripOfSixRows[row].length; column++) {
                assertEquals(stripOfSixRows[row][column], stripOfSix[row/ SINGLE_BINGO_ROWS][column + row % SINGLE_BINGO_ROWS * BINGO_COLUMNS ]);
            }
        }
    }

    @Test
    void stripOfSixColumnsToStripsTest() {
        fillWithNumbers(validStripOfSixColumns);
        int[][] stripOfSix = StripModelConverter.stripOfSixColumnsToStrips(validStripOfSixColumns);

        assertEquals(STRIP_OF_SIX_BINGO_COUNT, stripOfSix.length);

        for(int strip = 0; strip < stripOfSix.length; strip++) {
            assertEquals(SINGLE_BINGO_NUMBER_COUNT, stripOfSix[strip].length);
        }

        for(int column = 0; column < BINGO_COLUMNS; column++) {
            for(int row = 0; row < STRIP_OF_SIX_TOTAL_ROWS; row++) {
                assertEquals(validStripOfSixColumns[column][row], stripOfSix[row/ SINGLE_BINGO_ROWS][row% SINGLE_BINGO_ROWS *BINGO_COLUMNS+column]);
            }
        }
    }

    @Test
    void stripOfSixToColumnsTest() {
        int[][] stripOfSix = getAnInitialValidStripOfSix();
        fillWithNumbers(stripOfSix);
        int[][] stripOfSixColumns = StripModelConverter.stripOfSixToColumns(stripOfSix);

        assertEquals(BINGO_COLUMNS, stripOfSixColumns.length);

        for(int column = 0; column < stripOfSixColumns.length; column++) {
            assertEquals(STRIP_OF_SIX_TOTAL_ROWS, stripOfSixColumns[column].length);
            for(int row = 0; row < stripOfSixColumns[column].length; row++) {
                assertEquals(stripOfSixColumns[column][row], stripOfSix[row/3][row%3*9+column]);
            }
        }
    }

    @Test
    void stripOfSixRowsToRowTriplesTest() {
        fillWithNumbers(validStripOfSixColumns);
        int[][] stripOfSixColumnTriples = StripModelConverter.stripOfSixColumnsToColumnTriples(validStripOfSixColumns);

        assertEquals(TICKET_COLUMN_COUNT, stripOfSixColumnTriples.length);

        for(int tripleNumber = 0; tripleNumber < stripOfSixColumnTriples.length; tripleNumber++) {
            assertEquals(TRIPLE_LENGTH, stripOfSixColumnTriples[tripleNumber].length);

            for(int i = 0; i < TRIPLE_LENGTH; i++) {
                assertEquals(stripOfSixColumnTriples[tripleNumber][i], validStripOfSixColumns[tripleNumber / TRIPLE_COUNT_IN_A_COLUMN][(tripleNumber % TRIPLE_COUNT_IN_A_COLUMN) * TRIPLE_LENGTH + i]);
            }
        }
    }

    private void fillWithNumbers(int[][] stripOfSixRepresentation) {
        int randomValue = 1;
        for(int i = 0; i < stripOfSixRepresentation.length; i++) {
            for(int j = 0; j < stripOfSixRepresentation[i].length; j++) {
                if(stripOfSixRepresentation[i][j] != BLANK) {
                    stripOfSixRepresentation[i][j] = ++randomValue;
                }
            }
        }
    }
}
