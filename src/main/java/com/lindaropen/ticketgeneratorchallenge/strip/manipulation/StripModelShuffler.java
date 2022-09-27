package com.lindaropen.ticketgeneratorchallenge.strip.manipulation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.lindaropen.ticketgeneratorchallenge.strip.validation.InputValidation.*;
import static com.lindaropen.ticketgeneratorchallenge.strip.Constants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StripModelShuffler {
    public static void shuffleStripOfSix(int[][] stripOfSix) {
        validateStripOfSix(stripOfSix);

        List<int[]> shuffledList = Arrays.asList(stripOfSix);
        Collections.shuffle(shuffledList);

        for(int i = 0; i < shuffledList.size(); i++) {
            stripOfSix[i] = shuffledList.get(i);
        }
    }

    public static void shuffleRows(int[][] stripOfSixRows) {
        validateStripOfSixRows(stripOfSixRows);

        List<int[]> shuffledList = Arrays.asList(stripOfSixRows);

        for(int i = 0; i < STRIP_OF_SIX_BINGO_COUNT; i++) {
            List<int[]> oneStripRows = Arrays.asList(stripOfSixRows[i*3], stripOfSixRows[i*3+1], stripOfSixRows[i*3+2]);
            Collections.shuffle(oneStripRows);
            stripOfSixRows[i* SINGLE_BINGO_ROWS] = oneStripRows.get(0);
            stripOfSixRows[i* SINGLE_BINGO_ROWS +1] = oneStripRows.get(1);
            stripOfSixRows[i* SINGLE_BINGO_ROWS +2] = oneStripRows.get(2);
        }

        for(int i = 0; i < STRIP_OF_SIX_TOTAL_ROWS; i++) {
            stripOfSixRows[i] = shuffledList.get(i);
        }
    }

    public static void shuffleColumns(int[][] stripOfSixColumns) {
        validateStripOfSixColumns(stripOfSixColumns);

        List<int[]> shuffledList = new ArrayList<>();
        for(int i = 0; i < BINGO_COLUMNS; i++) {

            // First and last columns cannot be shuffled, because number of blanks are different on edges
            if(i != 0 && i != BINGO_COLUMNS-1) {
                shuffledList.add(stripOfSixColumns[i]);
            }
        }

        Collections.shuffle(shuffledList);

        for(int i = 0; i < BINGO_COLUMNS; i++) {
            if( i!= 0 && i != BINGO_COLUMNS-1) {
                stripOfSixColumns[i] = shuffledList.get(i-1);
            }
        }
    }
}
