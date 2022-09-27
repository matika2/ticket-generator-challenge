package com.lindaropen.ticketgeneratorchallenge.strip.validation;

import com.lindaropen.ticketgeneratorchallenge.exception.WrongInputException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.lindaropen.ticketgeneratorchallenge.strip.Constants.*;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InputValidation {
    public static void validateStripOfSixRows(int[][] stripOfSixRows) {
        validate(stripOfSixRows, STRIP_OF_SIX_TOTAL_ROWS, BINGO_COLUMNS);
    }

    public static void validateStripOfSixColumns(int[][] stripOfSixColumns) {
        validate(stripOfSixColumns, BINGO_COLUMNS, STRIP_OF_SIX_TOTAL_ROWS);
    }

    public static void validateStripOfSix(int[][] stripOfSix) {
        validate(stripOfSix, STRIP_OF_SIX_BINGO_COUNT, SINGLE_BINGO_NUMBER_COUNT);
    }

    public static void validateStripOfSixTriples(int[][] stripOfSixTriples) {
        validate(stripOfSixTriples, TICKET_COLUMN_COUNT , SINGLE_BINGO_ROWS);
    }

    private static void validate(int[][] array, int length, int innerLength) {
        if(array.length != length) {
            throw new WrongInputException();
        }

        for(int strip = 0; strip < array.length; strip++) {
            if(array[strip].length != innerLength) {
                throw new WrongInputException();
            }
        }
    }
}
