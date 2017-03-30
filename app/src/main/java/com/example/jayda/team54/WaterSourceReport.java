package com.example.jayda.team54;

import java.io.Serializable;

/**
 * Created by Emily on 2/24/2017.
 */

public class WaterSourceReport implements Serializable {

    //Many of these probably shouldn't actually be strings, im just not sure what they should be right now lol
    public String dateTime;
    public int reportNum;
    public String reporterName;
    public String waterLocation;
    public String waterType;
    public String waterCondition;

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
     * Getter for waterLocation
     * @return waterLocation The location of the water source
     */
    public String getWaterLocation() {
        return waterLocation;
    }
}
