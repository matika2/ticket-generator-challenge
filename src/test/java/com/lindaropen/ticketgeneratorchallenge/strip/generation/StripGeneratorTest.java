package com.lindaropen.ticketgeneratorchallenge.strip.generation;

import com.lindaropen.ticketgeneratorchallenge.helper.VerificationHelper;
import com.lindaropen.ticketgeneratorchallenge.strip.visualmodel.VisualStripOfSix;
import org.junit.jupiter.api.Test;

/**
 * Generating and validating random bingo strips
 */
class StripGeneratorTest {
    @Test
    void verifyArbitraryNumberOfStripOfSix() {
        // Number of strip of six to generate
        int limit = 10000;

        // Check conditions for each generated tickets
        boolean verify = true;

        // Print ticket to console
        boolean print = true;

        VisualStripOfSix visualStripOfSix = new VisualStripOfSix();

        System.out.println("Start generating " + limit + " number of strip of six bingo tickets.");
        long startMillis = System.currentTimeMillis();
        for(int strip = 1; strip <= limit; strip++) {
            int[][] stripOfSixColumns = StripGenerator.generateStripOfSixColumns();

            if(verify) {
                VerificationHelper.verifyStripOfSixColumns(stripOfSixColumns);
                visualStripOfSix.buildFromColumns(stripOfSixColumns);
            }

            if(print) {
                visualStripOfSix.buildFromColumns(stripOfSixColumns);
                System.out.println("-- Strip of six (" + strip + "/" + limit + ") --");
                System.out.println(visualStripOfSix);
            }
        }

        long finishMillis = System.currentTimeMillis();
        System.out.println("Generation of " + limit + " number of strip of six bingo tickets is complete in " + (finishMillis - startMillis) + "ms. (Including verification: " + verify + " & including printout: " + print + ")");
    }
}
