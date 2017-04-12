package com.example.jayda.team54;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Made by Emily Chen
 *
 * Junit that tests the validateGraph method in HistoryActivity
 */

public class HistoryActivityTest {

    private String location;
    private String year;

    /**
     * Check for out-of-bounds latitude
     */

    @Test
    public void oobLatitudeTest() {
        location = "459, 23";
        year = "1";
        assertFalse(HistoryActivity.validateGraph(location, year));
    }

    /**
     * Check for out-of-bounds longitude
     */

    @Test
    public void oobLongitudeTest() {
        location = "48, 395";
        year = "1";
        assertFalse(HistoryActivity.validateGraph(location, year));
    }

    /**
     * Check for invalid year
     */

    @Test
    public void invalidYearTest() {
        location = "34, 70";
        year = "wrong";
        assertFalse(HistoryActivity.validateGraph(location, year));
    }

    /**
     * Check for location without a comma
     */

    @Test
    public void missingCommaTest() {
        location = "100 100";
        year = "1";
        assertFalse(HistoryActivity.validateGraph(location, year));
    }

    /**
     * Check for valid location and year
     */

    @Test
    public void validEmailTest() {
        location = "40, 84";
        year = "1";
        assertTrue(HistoryActivity.validateGraph(location, year));
    }
}