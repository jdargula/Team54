package com.example.jayda.team54;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Junit created to test locationCheck method for latitude and longitude fields.
 */
public class WaterSourceReportTest {

    private String dateTime;
    private int reportNum;
    private String reporterName;
    private String waterLocation;
    private String waterType;
    private String waterCondition;

    @Before
    public void setup() {
        dateTime = "4/10/17";
        reportNum = 1;
        reporterName = "Fayaz Josufi";
        waterLocation = "0, 0";
        waterType = "Bottled";
        waterCondition = "Potable";
    }

    /**
     * Helper method to make a water source report
     * @return a new water source report with given fields
     */

    public WaterSourceReport makeReport() {
        return new WaterSourceReport(dateTime, reportNum, reporterName, waterLocation, waterType,
                waterCondition);
    }

    /**
     * Check invalid latitude
     */

    @Test
    public void latitudeNotWithinBounds() {
        waterLocation = "91, 180";
        WaterSourceReport waterSourceReport = makeReport();
        assertFalse(waterSourceReport.locationCheck());
    }

    /**
     * Check invalid longitude
     */

    @Test
    public void longitudeNotWithinBounds() {
        waterLocation = "90, 181";
        WaterSourceReport waterSourceReport = makeReport();
        assertFalse(waterSourceReport.locationCheck());
    }

    /**
     * Check invalid latitude and longitude
     */

    @Test
    public void latAndLongNotWithinBounds() {
        waterLocation = "91, 181";
        WaterSourceReport waterSourceReport = makeReport();
        assertFalse(waterSourceReport.locationCheck());
    }

    /**
     * Check valid latitude and longitude
     */

    @Test
    public void latAndLongWithinBounds() {
        waterLocation = "90, 180";
        WaterSourceReport waterSourceReport = makeReport();
        assertTrue(waterSourceReport.locationCheck());
    }
}