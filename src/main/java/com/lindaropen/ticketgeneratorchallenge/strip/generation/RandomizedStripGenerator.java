package com.lindaropen.ticketgeneratorchallenge.strip.generation;

import com.lindaropen.ticketgeneratorchallenge.exception.InvalidStripException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

import static com.lindaropen.ticketgeneratorchallenge.strip.Constants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomizedStripGenerator {
    // Used to mark a field cannot be set to blank i.e. it is a number
    private static final int NON_BLANK = -2;

    private static final Random random = new Random();

    /**
     * Following variables are used as optimization in case random position cannot be blank.
     */
    private static boolean[] columnsNotAcceptingBlanks = new boolean[BINGO_COLUMNS];
    private static boolean[] rowsNotAcceptingBlanks = new boolean[STRIP_OF_SIX_TOTAL_ROWS];
    private static boolean[] ticketColumnsNotAcceptingBlanks = new boolean[TICKET_COLUMN_COUNT];

    // I had difficulties generating valid strip based on rules every time. If generated ticket is faulty,
    // then generating a new one.
    public static int[][] generateRandomStripUntilSucceeds() {
        boolean failure;
        int[][] randomStrip = new int[0][];
        do {
            try {
                randomStrip = generateRandomStrip();
                failure = false;
            } catch(Exception e) {
                failure = true;
            }

        } while(failure);

        return randomStrip;
    }

    public static int[][] generateRandomStrip() throws InvalidStripException {
        columnsNotAcceptingBlanks = new boolean[BINGO_COLUMNS];
        rowsNotAcceptingBlanks = new boolean[STRIP_OF_SIX_TOTAL_ROWS];
        ticketColumnsNotAcceptingBlanks = new boolean[TICKET_COLUMN_COUNT];

        // Generating a fully blank ticket
        int[][] randomColumnarStrip = new int[BINGO_COLUMNS][];

        for(int column = 0; column < BINGO_COLUMNS; column++) {
            randomColumnarStrip[column] = new int[STRIP_OF_SIX_TOTAL_ROWS];
        }

        for(int blankCount = 0; blankCount < TOTAL_BLANK_COUNT; blankCount++) {
            int randomField = getNextFreeField(randomColumnarStrip);
            int randomColumn = randomField / STRIP_OF_SIX_TOTAL_ROWS;
            int randomRow = randomField % STRIP_OF_SIX_TOTAL_ROWS;

            randomColumnarStrip[randomColumn][randomRow] = BLANK;

            boolean nonBlanksSet = false;

            int ticketColumn = randomField/TRIPLE_LENGTH;
            if(!ticketColumnsNotAcceptingBlanks[ticketColumn] && !isTicketColumnAcceptMoreBlanks(randomColumnarStrip, randomRow, randomColumn)) {
                setNoMoreBlanksAcceptedByTicketColumn(randomColumnarStrip, randomRow, randomColumn);
                ticketColumnsNotAcceptingBlanks[ticketColumn] = true;
                nonBlanksSet = true;
            }

            if(!rowsNotAcceptingBlanks[randomRow] && !isTicketRowAcceptMoreBlanks(randomColumnarStrip, randomRow)) {
                setNoMoreBlanksAcceptedByTicketRow(randomColumnarStrip, randomRow);
                rowsNotAcceptingBlanks[randomRow] = true;
                nonBlanksSet = true;
            }

            if(!columnsNotAcceptingBlanks[randomColumn] && !isStripColumnAcceptMoreBlanks(randomColumnarStrip, randomColumn)) {
                setNoMoreBlanksAcceptedByStripColumn(randomColumnarStrip, randomColumn);
                columnsNotAcceptingBlanks[randomColumn] = true;
                nonBlanksSet = true;
            }

            if(nonBlanksSet) {
                validateStrip(randomColumnarStrip, randomRow, randomColumn);
            }

        }

        StripNumberGenerator.generateNumbersToStripOfSixColumns(randomColumnarStrip);

        return randomColumnarStrip;
    }

    private static void validateStrip(int[][] columnarStrip, int row, int column) throws InvalidStripException {
        if(!columnsNotAcceptingBlanks[column] && isStripColumnInvalid(columnarStrip, column)) {
            throw new InvalidStripException("Failed to generate strip - strip column is invalid.");
        }

        if(!rowsNotAcceptingBlanks[row] && isTicketRowInvalid(columnarStrip, row)) {
            throw new InvalidStripException("Failed to generate strip - ticket row is invalid.");
        }

        // It is possible that a ticket column has 0 blanks.
        // Generation process makes sure no more than 2 blanks are in the ticket column.
        // Therefore, the assumption is the ticket column is always valid.
    }


    private static int getRandomField() {
        return random.nextInt(TOTAL_FIELDS_ON_A_STRIP_OF_SIX);
    }
    /**
     * Doing shortcuts instead of iterating over the whole strip when random field cannot be blank.
     * If a column is full, then let's jump to next column
     * If a row is full, then let's jump to next row. Doing similarly in case ticket column is full
     */
    private static int getNextFreeField(int[][] columnarStrip) throws InvalidStripException {
        int randomField = getRandomField();
        int randomColumn = randomField / STRIP_OF_SIX_TOTAL_ROWS;
        int randomRow = randomField % STRIP_OF_SIX_TOTAL_ROWS;

        if(isFieldFree(columnarStrip, randomRow, randomColumn)) {
            return randomField;
        }

        int nextColumn = randomColumn;
        int nextRow = randomRow;
        for(int column = 0; column < BINGO_COLUMNS; column++) {
            if(columnsNotAcceptingBlanks[nextColumn]) {
                nextColumn = getPossiblyOverflownNextColumn(nextColumn);
                continue;
            }

            for(int row = 0; row < STRIP_OF_SIX_TOTAL_ROWS; row++) {
                if(rowsNotAcceptingBlanks[nextRow] || ticketColumnsNotAcceptingBlanks[(nextColumn*STRIP_OF_SIX_TOTAL_ROWS + nextRow)/3]) {
                    nextRow = getPossiblyOverflownNextRow(nextRow);
                    continue;
                }

                if(isFieldFree(columnarStrip, nextRow, nextColumn)) {
                    return nextColumn * STRIP_OF_SIX_TOTAL_ROWS + nextRow;
                }

                nextRow = getPossiblyOverflownNextRow(nextRow);
            }

            nextColumn++;
        }

        throw new InvalidStripException("No free fields found.");
    }

    private static int getPossiblyOverflownNextRow(int row) {
        if(++row >= STRIP_OF_SIX_TOTAL_ROWS) {
            row = 0;
        }

        return row;
    }

    public static int getPossiblyOverflownNextColumn(int column) {
        if(++column >= BINGO_COLUMNS) {
            column = 0;
        }

        return column;
    }

    private static boolean isFieldFree(int[][] columnarStrip, int row, int column) {
        return columnarStrip[column][row] != BLANK && columnarStrip[column][row] != NON_BLANK;
    }

    private static boolean isTicketColumnAcceptMoreBlanks(int[][] columnarStrip, int row, int column) {
        // Identifying the first field of a ticket column
        int columnStartRow = row - row % SINGLE_BINGO_ROWS;
        int blankCount = 0;
        for(int rowToValidate = columnStartRow; rowToValidate < columnStartRow + SINGLE_BINGO_ROWS; rowToValidate++) {
            if(columnarStrip[column][rowToValidate] == BLANK) {
                blankCount++;
            }
        }

        return blankCount < MAX_BLANKS_IN_A_TRIPLE;
    }

    private static void setNoMoreBlanksAcceptedByTicketColumn(int[][] columnarStrip, int row, int column) {
        // Identifying the first field of a ticket column
        int columnStartRow = row - row % SINGLE_BINGO_ROWS;
        for(int rowToInvalidate = columnStartRow; rowToInvalidate < columnStartRow + SINGLE_BINGO_ROWS; rowToInvalidate++) {
            if(columnarStrip[column][rowToInvalidate] != BLANK) {
                columnarStrip[column][rowToInvalidate] = NON_BLANK;
            }
        }
    }

    private static boolean isTicketRowAcceptMoreBlanks(int[][] columnarStrip, int row) {
        int blankCount = 0;
        for(int columnToValidate = 0; columnToValidate < BINGO_COLUMNS && blankCount < BLANKS_IN_ROW; columnToValidate++) {
            if(columnarStrip[columnToValidate][row] == BLANK) {
                blankCount++;
            }
        }

        return blankCount < BLANKS_IN_ROW;
    }


    private static boolean isTicketRowInvalid(int[][] columnarStrip, int row) {
        for(int columnToValidate = 0; columnToValidate < BINGO_COLUMNS; columnToValidate++) {
            if(columnarStrip[columnToValidate][row] != BLANK && columnarStrip[columnToValidate][row] != NON_BLANK) {
                return false;
            }
        }

        return true;
    }

    private static void setNoMoreBlanksAcceptedByTicketRow(int[][] columnarStrip, int row) {

         for(int columnToValidate = 0; columnToValidate < BINGO_COLUMNS; columnToValidate++) {
             if(columnarStrip[columnToValidate][row] != BLANK) {
                 columnarStrip[columnToValidate][row] = NON_BLANK;
             }
        }
    }

    private static boolean isStripColumnAcceptMoreBlanks(int[][] columnarStrip, int column) {
        int blankCount = 0;
        for(int rowToValidate = 0; rowToValidate < STRIP_OF_SIX_TOTAL_ROWS; rowToValidate++) {
            if(columnarStrip[column][rowToValidate] == BLANK) {
                blankCount++;
            }
        }

        if(column == 0) {
            return blankCount < BLANKS_IN_FIRST_COLUMN;
        } else if(column == BINGO_COLUMNS-1) {
            return blankCount < BLANKS_IN_LAST_COLUMN;
        }

        return blankCount < BLANKS_IN_NONE_EDGE_COLUMN;
    }

    private static boolean isStripColumnInvalid(int[][] columnarStrip, int column) {
        for(int rowToValidate = 0; rowToValidate < STRIP_OF_SIX_TOTAL_ROWS; rowToValidate++) {
            if(columnarStrip[column][rowToValidate] != BLANK && columnarStrip[column][rowToValidate] != NON_BLANK) {
                return false;
            }
        }

        return true;
    }

    private static void setNoMoreBlanksAcceptedByStripColumn(int[][] columnarStrip, int column) {
        for(int rowToValidate = 0; rowToValidate < STRIP_OF_SIX_TOTAL_ROWS; rowToValidate++) {
            if(columnarStrip[column][rowToValidate] != BLANK) {
                columnarStrip[column][rowToValidate] = NON_BLANK;
            }
        }
    }
}
