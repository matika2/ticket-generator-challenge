package com.lindaropen.ticketgeneratorchallenge.strip.manipulation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.lindaropen.ticketgeneratorchallenge.strip.Constants.*;
import static com.lindaropen.ticketgeneratorchallenge.strip.validation.InputValidation.*;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StripModelConverter {
    // columns -> rows
    public static int[][] stripOfSixColumnsToRows(int[][] stripOfSixColumns) {
        validateStripOfSixColumns(stripOfSixColumns);

        int[][] stripOfSixRows = new int[STRIP_OF_SIX_TOTAL_ROWS][];
        for(int i = 0; i < stripOfSixRows.length; i++) {
            stripOfSixRows[i] = new int[BINGO_COLUMNS];
        }

        for(int columns = 0; columns < stripOfSixColumns.length; columns++) {
            for(int stripOfSixRow = 0; stripOfSixRow < STRIP_OF_SIX_TOTAL_ROWS; stripOfSixRow++) {
                stripOfSixRows[stripOfSixRow][columns] = stripOfSixColumns[columns][stripOfSixRow];
            }
        }

        return stripOfSixRows;
    }

    // rows -> columns
    public static int[][] stripOfSixRowsToColumns(int[][] stripOfSixRows) {
        validateStripOfSixRows(stripOfSixRows);

        int[][] stripOfSixColumns = new int[BINGO_COLUMNS][];

        for(int bingo = 0; bingo < stripOfSixColumns.length; bingo++) {
            stripOfSixColumns[bingo] = new int[STRIP_OF_SIX_TOTAL_ROWS];
        }

        for(int stripOfSixRow = 0; stripOfSixRow < STRIP_OF_SIX_TOTAL_ROWS; stripOfSixRow++) {
            for(int column = 0; column < BINGO_COLUMNS; column++) {
                stripOfSixColumns[column][stripOfSixRow] = stripOfSixRows[stripOfSixRow][column];
            }
        }

        return stripOfSixColumns;
    }

    // rows -> strips
    public static int[][] stripOfSixRowsToStrips(int[][] stripOfSixRows) {
        validateStripOfSixRows(stripOfSixRows);

        int[][] stripOfSix = new int[STRIP_OF_SIX_BINGO_COUNT][];

        for(int strip = 0; strip < stripOfSix.length; strip++) {
            stripOfSix[strip] = new int[SINGLE_BINGO_NUMBER_COUNT];
        }

        for(int strip = 0; strip < STRIP_OF_SIX_BINGO_COUNT; strip++) {
            for(int bingoRow = 0; bingoRow < SINGLE_BINGO_ROWS; bingoRow++) {
                for(int bingoColumn = 0; bingoColumn < BINGO_COLUMNS; bingoColumn++) {
                    stripOfSix[strip][bingoRow * 9 + bingoColumn] = stripOfSixRows[(strip * 3 + bingoRow)][bingoColumn];
                }
            }
        }

        return stripOfSix;
    }

    // strips -> rows
    public static int[][] stripOfSixToRows(int[][] stripOfSix) {
        validateStripOfSix(stripOfSix);

        int[][] stripOfSixRows = new int[STRIP_OF_SIX_TOTAL_ROWS][];

        for(int stripOfSixRow = 0; stripOfSixRow < stripOfSixRows.length; stripOfSixRow++) {
            stripOfSixRows[stripOfSixRow] = new int[BINGO_COLUMNS];
        }

        for(int strip = 0; strip < STRIP_OF_SIX_BINGO_COUNT; strip++) {
            for(int bingoRow = 0; bingoRow < SINGLE_BINGO_ROWS; bingoRow++) {
                for(int column = 0; column < BINGO_COLUMNS; column++) {
                    stripOfSixRows[(strip * SINGLE_BINGO_ROWS + bingoRow)][column] = stripOfSix[strip][bingoRow * BINGO_COLUMNS + column];
                }
            }
        }

        return stripOfSixRows;
    }

    // columns -> strips
    public static int[][] stripOfSixColumnsToStrips(int[][] stripOfSixColumns) {
        validateStripOfSixColumns(stripOfSixColumns);

        int[][] stripOfSix = new int[STRIP_OF_SIX_BINGO_COUNT][];
        for(int strip = 0; strip < stripOfSix.length; strip++) {
            stripOfSix[strip] = new int[SINGLE_BINGO_NUMBER_COUNT];
        }

        for(int columns = 0; columns < stripOfSixColumns.length; columns++) {
            for(int stripOfSixRows = 0; stripOfSixRows < STRIP_OF_SIX_TOTAL_ROWS; stripOfSixRows++) {
                stripOfSix[stripOfSixRows/ SINGLE_BINGO_ROWS][stripOfSixRows % SINGLE_BINGO_ROWS * BINGO_COLUMNS + columns] = stripOfSixColumns[columns][stripOfSixRows];
            }
        }

        return stripOfSix;
    }

    // strips -> columns
    public static int[][] stripOfSixToColumns(int[][] stripOfSix) {
        validateStripOfSix(stripOfSix);

        int[][] stripOfSixColumns = new int[BINGO_COLUMNS][];
        for(int i = 0; i < stripOfSixColumns.length; i++) {
            stripOfSixColumns[i] = new int[STRIP_OF_SIX_TOTAL_ROWS];
        }

        for(int bingo = 0; bingo < STRIP_OF_SIX_BINGO_COUNT; bingo++) {
            for(int bingoPosition = 0; bingoPosition < SINGLE_BINGO_NUMBER_COUNT; bingoPosition++) {
                stripOfSixColumns[bingoPosition % BINGO_COLUMNS][bingo * SINGLE_BINGO_ROWS + bingoPosition / BINGO_COLUMNS] = stripOfSix[bingo][bingoPosition];
            }
        }

        return stripOfSixColumns;
    }

    // columns -> triples
    public static int[][] stripOfSixColumnsToColumnTriples(int[][] stripOfSixColumns) {
        validateStripOfSixColumns(stripOfSixColumns);

        int[][] stripOfSixTicketColumns = new int[TICKET_COLUMN_COUNT][];

        for(int i = 0; i < TICKET_COLUMN_COUNT; i++) {
            stripOfSixTicketColumns[i] = new int[SINGLE_BINGO_ROWS];
        }

        for(int column = 0; column < BINGO_COLUMNS; column++) {
            for(int stripOfSixRow = 0; stripOfSixRow < STRIP_OF_SIX_TOTAL_ROWS; stripOfSixRow++) {
                stripOfSixTicketColumns[(column * STRIP_OF_SIX_TOTAL_ROWS + stripOfSixRow) / SINGLE_BINGO_ROWS][stripOfSixRow % SINGLE_BINGO_ROWS] = stripOfSixColumns[column][stripOfSixRow];
            }
        }

        return stripOfSixTicketColumns;
    }

}
