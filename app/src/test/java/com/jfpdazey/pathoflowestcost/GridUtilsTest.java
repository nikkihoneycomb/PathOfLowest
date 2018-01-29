package com.jfpdazey.pathoflowestcost;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/*
Name: GridUtilsTest
Purpose: Test to see the grid and its validations on operations being performed
 */
public class GridUtilsTest {

    //test case to create empty grid and with null input
    @Test
    public void createsEmptyGridArrayWithNullInput() {
        assertThat(GridUtils.gridArrayFromString(null), equalTo(new int[0][0]));
    }

    //test case to create empty grid and with any non-numeric input value
    @Test
    public void createsEmptyGridArrayWithAnyNonNumericInput() {
        assertThat(GridUtils.gridArrayFromString("1 2 3 a 5"), equalTo(new int[0][0]));
    }

    //test to create onlin eof grid array with one string data
    @Test
    public void createsOneLineGridArrayWithOneLineOfStringData() {
        assertThat(GridUtils.gridArrayFromString("1  2   3  4 5"), equalTo(new int[][]{ { 1, 2, 3, 4, 5 } }));
    }

    //test to create multi line array
    @Test
    public void createsMultiLineGridArrayWithMultipleLinesOfStringData() {
        assertThat(GridUtils.gridArrayFromString("1  2   3  4 5\n6 7 8  9\t10"),
                equalTo(new int[][]{ { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 } }));
    }

    //test to create first line with row length and Extra Numbers In Later Lines Are Discarded
    @Test
    public void lengthOfFirstLineDeterminesRowLengthAndExtraNumbersInLaterLinesAreDiscarded() {
        assertThat(GridUtils.gridArrayFromString("1 2 3\n6 7 8 9 10"),
                equalTo(new int[][]{ { 1, 2, 3 }, { 6, 7, 8 } }));
    }

    //test to check length Of First Line Determines Row Length And Missing Numbers In Later Lines Are Zero
    @Test
    public void lengthOfFirstLineDeterminesRowLengthAndMissingNumbersInLaterLinesAreZero() {
        assertThat(GridUtils.gridArrayFromString("1 2 3 4 5\n6 7 8   "),
                equalTo(new int[][]{ { 1, 2, 3, 4, 5 }, { 6, 7, 8, 0, 0 } }));
    }
}