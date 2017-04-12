package com.example.jayda.team54;

import java.io.Serializable;

/**
 * Information submitted for water source reports.
 */

@SuppressWarnings("FieldCanBeLocal")
class WaterSourceReport implements Serializable {

    private final String dateTime;
    private final int reportNum;
    private final String reporterName;
    private final String waterLocation;
    private final String waterType;
    private final String waterCondition;

    public static final int MAX_LAT = 90;
    public static final int MAX_LONG = 180;

    /**
     * Class constructor with params for dateTime, reportNum, reporterName, waterLocation, waterType, and waterCondition.
     * @param dateTime Date and time the report was created.
     * @param reportNum Report number which indicates when it was created relative to other reports.
     * @param reporterName The name of the user who submitted the report.
     * @param waterLocation The location of the water source.
     * @param waterType The type of water
     * @param waterCondition The condition of the water, how drinkable it is
     */

    public WaterSourceReport(String dateTime, int reportNum, String reporterName, String waterLocation, String waterType, String waterCondition) {
        this.dateTime = dateTime;
        this.reportNum = reportNum;
        this.reporterName = reporterName;
        this.waterLocation = waterLocation;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
    }

    /**
     * Method to validate latitude and longitude inputs from water location
     * @return check - true or false based on validity
     */

    public boolean locationCheck() {
        boolean check = true;
        String[] locArr = waterLocation.split(",");
        int latitude = Integer.parseInt(locArr[0].trim());
        int longitude = Integer.parseInt(locArr[1].trim());
        if (Math.abs(latitude) > MAX_LAT) {
            check = false;
        }
        if (Math.abs(longitude) > MAX_LONG) {
            check = false;
        }
        return check;
    }
}
