package com.lindaropen.ticketgeneratorchallenge.strip;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    // Blank space is represented by -1
    public static final int BLANK = -1;

    // By definition each ticket row contains 4 blanks
    public static final int BLANKS_IN_ROW = 4;

    // One column is of 18 rows. First column contains 9 numbers, the rest (18 - 9 = 9) is blank.
    public static final int BLANKS_IN_FIRST_COLUMN = 9;

    // One column is of 18 rows. First column contains 11 numbers, the rest (18 - 11 = 7) is blank.
    public static final int BLANKS_IN_LAST_COLUMN = 7;

    // One column is of 18 rows. Non-first and non-last columns contain 10 numbers, the rest (18 - 10 = 8) is blank.
    public static final int BLANKS_IN_NONE_EDGE_COLUMN = 8;

    // By definition a bingo ticket consists of 3 rows
    public static final int SINGLE_BINGO_ROWS = 3;

    // By definition a bingo ticket consists of 9 rows
    public static final int BINGO_COLUMNS = 9;

    // By definition each ticket column consists of one, two or three numbers and never three blanks.
    public static final int MAX_BLANKS_IN_A_TRIPLE = 2;

    // Ticket column row count. By definition each ticket consists of 3 rows.
    public static final int TRIPLE_LENGTH = 3;

    // 6 bingo tickets of 3 rows each => 6 * 3 = 18 rows in total
    public static final int STRIP_OF_SIX_TOTAL_ROWS = 18;

    // A bingo ticket is logically split into ticket columns of 3 numbers.
    // A strip of six consists of 6 tickets of 3 rows of 9 numbers = 6 * 3 * 9 = 162
    // Therefore ticket column count = 162 / 3 = 54
    public static final int TICKET_COLUMN_COUNT = 54;

    // One column is of 18 rows, therefore it consists of 18 / 3 = 6 triples
    public static final int TRIPLE_COUNT_IN_A_COLUMN = 6;

    // By definition a bingo ticket contains number from 1 to 90
    public static final int BINGO_MIN_NUMBER = 1;
    public static final int BINGO_MAX_NUMBER = 90;

    // A strip of six consists of 6 bingo tickets.
    public static final int STRIP_OF_SIX_BINGO_COUNT = 6;

    // A bingo ticket consists of 3 rows of 9 numbers => 3 * 9 = 27 numbers in total
    public static final int SINGLE_BINGO_NUMBER_COUNT = 27;

    // Taking a valid strip of six as basis of ticket generation
    private static final int[][] INITIAL_VALID_STRIP_OF_SIX = {
            {       0,0,BLANK,BLANK,0,0,0,BLANK,BLANK,
                    BLANK,BLANK,BLANK,0,0,0,BLANK,0,0,
                    BLANK,0,0,0,BLANK,0,BLANK,BLANK,0},

            {       BLANK,BLANK,BLANK,0,0,0,BLANK,0,0,
                    0,0,BLANK,0,0,BLANK,0,BLANK,BLANK,
                    BLANK,0,0,0,BLANK,BLANK,0,BLANK,0},

            {       0,0,BLANK,BLANK,0,BLANK,0,0,BLANK,
                    0,BLANK,BLANK,BLANK,0,0,BLANK,0,0,
                    0,BLANK,0,0,BLANK,0,0,BLANK,BLANK},

            {       0,0,BLANK,BLANK,BLANK,0,BLANK,0,0,
                    BLANK,BLANK,0,0,BLANK,BLANK,0,0,0,
                    BLANK,0,0,BLANK,0,0,BLANK,BLANK,0},

            {       0,BLANK,0,0,0,BLANK,0,BLANK,BLANK,
                    0,0,0,BLANK,BLANK,0,BLANK,0,BLANK,
                    BLANK,BLANK,0,0,0,BLANK,BLANK,0,0},

            {       0,BLANK,0,BLANK,BLANK,0,0,0,BLANK,
                    BLANK,0,0,BLANK,0,BLANK,0,BLANK,0,
                    BLANK,0,BLANK,0,BLANK,BLANK,0,0,0}
    };

    public static int[][] getAnInitialValidStripOfSix() {
        int[][] initialStripOfSixCopy = new int[INITIAL_VALID_STRIP_OF_SIX.length][];

        for (int strip = 0; strip < INITIAL_VALID_STRIP_OF_SIX.length; strip++) {
            initialStripOfSixCopy[strip] = new int[INITIAL_VALID_STRIP_OF_SIX[strip].length];
            System.arraycopy(INITIAL_VALID_STRIP_OF_SIX[strip], 0, initialStripOfSixCopy[strip], 0, INITIAL_VALID_STRIP_OF_SIX[strip].length);
        }

        return initialStripOfSixCopy;
    }
}
