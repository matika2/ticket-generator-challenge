package com.lindaropen.ticketgeneratorchallenge.strip.generation;

import com.lindaropen.ticketgeneratorchallenge.strip.manipulation.StripModelConverter;
import com.lindaropen.ticketgeneratorchallenge.strip.manipulation.StripModelShuffler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.lindaropen.ticketgeneratorchallenge.strip.Constants.*;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StripGenerator {
    public static int[][] generateStripOfSixColumns() {
        int[][] stripOfSixToShuffle = getAnInitialValidStripOfSix();
        StripModelShuffler.shuffleStripOfSix(stripOfSixToShuffle);

        int[][] stripOfSixRowsToShuffle = StripModelConverter.stripOfSixToRows(stripOfSixToShuffle);
        StripModelShuffler.shuffleRows(stripOfSixRowsToShuffle);

        int[][] stripOfSixColumnsToShuffle = StripModelConverter.stripOfSixRowsToColumns(stripOfSixRowsToShuffle);
        StripModelShuffler.shuffleColumns(stripOfSixColumnsToShuffle);

        StripNumberGenerator.generateNumbersToStripOfSixColumns(stripOfSixColumnsToShuffle);

        return stripOfSixColumnsToShuffle;
    }
}
