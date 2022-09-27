package com.lindaropen.ticketgeneratorchallenge.helper;

import com.lindaropen.ticketgeneratorchallenge.exception.visualmodel.InvalidBingoNumberException;
import com.lindaropen.ticketgeneratorchallenge.strip.manipulation.StripModelConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.lindaropen.ticketgeneratorchallenge.strip.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.lindaropen.ticketgeneratorchallenge.strip.validation.InputValidation.*;

public class VerificationHelper {
    /**
     * Verification of all conditions described in Ticket Generator Challenge description
     * @param stripOfSixColumns Columnar representation of a strip of six
     */
    public static void verifyStripOfSixColumns(int[][] stripOfSixColumns) {
        int[][] stripOfSixRows = StripModelConverter.stripOfSixColumnsToRows(stripOfSixColumns);
        int[][] stripOfSixTriples = StripModelConverter.stripOfSixColumnsToColumnTriples(stripOfSixColumns);

        verifyAll(stripOfSixRows, stripOfSixColumns, stripOfSixTriples);
    }

    private static void verifyAll(int[][] stripOfSixRows, int[][] stripOfSixColumns, int[][] stripOfSixTriples) {
        validateStripOfSixRows(stripOfSixRows);
        validateStripOfSixColumns(stripOfSixColumns);
        validateStripOfSixTriples(stripOfSixTriples);

        verifyStripColumnsBlanks(stripOfSixColumns);
        verifyTicketColumnBlanks(stripOfSixTriples);
        verifyStripRowsBlanks(stripOfSixRows);
        verifyStripColumnTriplesAscending(stripOfSixTriples);
        verifyNumbersInColumns(stripOfSixColumns);
        verifyAllPossibleNumbersAreCoveredInColumns(stripOfSixColumns);
    }

    private static void verifyNumbersInColumns(int[][] stripOfSixColumns) {
        for(int column = 0; column < stripOfSixColumns.length; column++) {
            int min;
            int max;
            if(column == 0) {
                min = BINGO_MIN_NUMBER;
                max = 9;
            } else if (column == stripOfSixColumns.length - 1) {
                min = 80;
                max = BINGO_MAX_NUMBER;
            } else {
                min = column * 10;
                max = min + 9;
            }
            for(int row = 0; row < stripOfSixColumns[column].length; row++) {
                if(stripOfSixColumns[column][row] != BLANK && (stripOfSixColumns[column][row] < min || stripOfSixColumns[column][row] > max) ) {
                    throw new InvalidBingoNumberException();
                }
            }
        }
    }

    private static void verifyAllPossibleNumbersAreCoveredInColumns(int[][] stripOfSixColumns) {
        Set<Integer> numbers = new HashSet<>();

        for(int column = 0; column < stripOfSixColumns.length; column++) {
            for(int row = 0; row < stripOfSixColumns[column].length; row++) {
                numbers.add(stripOfSixColumns[column][row]);
            }
        }

        assertTrue(numbers.stream().allMatch(num -> num == BLANK || num >= BINGO_MIN_NUMBER && num <= BINGO_MAX_NUMBER));
        assertEquals(BINGO_MAX_NUMBER, numbers.stream().filter(num -> num >= BINGO_MIN_NUMBER && num <= BINGO_MAX_NUMBER).count());
    }


    private static void verifyStripColumnTriplesAscending(int[][] stripColumnTriples) {
        for(int i = 0; i < stripColumnTriples.length; i++) {
            int[] columnTriple = stripColumnTriples[i];
            assertTrue(isSmaller(columnTriple[0], columnTriple[1]));
            assertTrue(isSmaller(columnTriple[0], columnTriple[2]));
            assertTrue(isSmaller(columnTriple[1], columnTriple[2]));
        }
    }

    private static boolean isSmaller(int value1, int value2) {
        return value1 == BLANK || value2 == BLANK || value1 < value2;
    }

    private static void verifyTicketColumnBlanks(int[][] triples) {
        assertEquals(TICKET_COLUMN_COUNT, triples.length);

        for(int i = 0; i < TICKET_COLUMN_COUNT; i++) {
            assertEquals(SINGLE_BINGO_ROWS, triples[i].length);
            assertTrue(getBlankCount(triples[i]) <= MAX_BLANKS_IN_A_TRIPLE);
        }
    }

    private static void verifyStripColumnsBlanks(int[][] columns) {
        assertEquals(BINGO_COLUMNS, columns.length);

        assertEquals(BLANKS_IN_FIRST_COLUMN, getBlankCount(columns[0]));
        assertEquals(BLANKS_IN_LAST_COLUMN, getBlankCount(columns[columns.length-1]));

        for(int nonEdgeColumn = 1; nonEdgeColumn < columns.length - 1; nonEdgeColumn++) {
            assertEquals(BLANKS_IN_NONE_EDGE_COLUMN, getBlankCount(columns[nonEdgeColumn]));
        }
    }

    private static void verifyStripRowsBlanks(int[][] rows) {
        assertEquals(STRIP_OF_SIX_TOTAL_ROWS, rows.length);

        for(int i = 0; i < rows.length; i++) {
            assertEquals(BINGO_COLUMNS, rows[i].length);
            assertEquals(BLANKS_IN_ROW, getBlankCount(rows[i]));
        }
    }

    private static int getBlankCount(int[] values) {
        return (int) Arrays.stream(values).filter(value -> value == BLANK).count();
    }
}
