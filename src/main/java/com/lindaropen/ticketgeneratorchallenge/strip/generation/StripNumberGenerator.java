package com.lindaropen.ticketgeneratorchallenge.strip.generation;

import com.lindaropen.ticketgeneratorchallenge.exception.WrongInputException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.lindaropen.ticketgeneratorchallenge.strip.Constants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StripNumberGenerator {
    public static void generateNumbersToStripOfSixColumns(int[][] stripOfSixColumns) {
        for(int column = 0; column < 9; column++){
            int min = column * 10 == 0 ? BINGO_MIN_NUMBER : column * 10;
            int max = (column + 1) * 10 == BINGO_MAX_NUMBER ? BINGO_MAX_NUMBER : (column + 1) * 10 - 1;

            List<Integer> columnNumbers = new ArrayList<>();
            for(int i = 0; i < max - min + 1; i++) {
                columnNumbers.add(i + min);
            }
            Collections.shuffle(columnNumbers);

            int[] randomColumnNumbers = columnNumbers.stream().mapToInt(Integer::intValue).toArray();

            fillColumnAscending(stripOfSixColumns[column], randomColumnNumbers);
        }
    }

    private static void swap(int position1, int position2, int[] array) {
        int temp = array[position1];
        array[position1] = array[position2];
        array[position2] = temp;
    }

    private static void fillColumnAscending(int[] column, int[] numbers) {
        if(column.length != STRIP_OF_SIX_TOTAL_ROWS) {
            throw new WrongInputException();
        }
        int numbersIndex = 0;

        for(int stripPart = 0; stripPart < 6; stripPart++) {
            for(int i = 0; i < 3; i++) {
                if(column[stripPart*3+i] != BLANK) {
                    column[stripPart*3+i] = numbers[numbersIndex];
                    numbersIndex++;
                }
            }

            // Sorting strip part
            orderSwap(stripPart*3, stripPart*3+2, column);
            orderSwap(stripPart*3+1, stripPart*3+2, column);
            orderSwap(stripPart*3, stripPart*3+1, column);
        }
    }

    private static void orderSwap(int position1, int position2, int[] array) {
        if(array[position1] == BLANK || array[position2] == BLANK) {
            return;
        }

        if(array[position1] > array[position2]) {
            swap(position1, position2, array);
        }
    }
}
