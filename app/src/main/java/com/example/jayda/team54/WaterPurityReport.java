package com.example.jayda.team54;

/**
 * Created by Emily on 3/14/2017.
 */

public class WaterPurityReport {

    public String dateTime;
    public int reportNum;
    public String workerName;
    public String waterLocation;
    public String waterCondition;
    public int virusPPM;
    public int contaminantPPM;

    /**
     * Class constructor with params for dateTime, reportNum, workerName, waterLocation, waterCondition, virusPPM, and contaminantPPM.
     * @param dateTime Date and time the report was created.
     * @param reportNum Report number which indicates when it was created relative to other reports.
     * @param workerName The name of the worker who submitted the report.
     * @param waterLocation The location of the water source being evaluated.
     * @param waterCondition The condition of the water source being evaluated.
     * @param virusPPM The level of viruses in the water source.
     * @param contaminantPPM The level of contamination in the water source.
     */
    public WaterPurityReport(String dateTime, int reportNum, String workerName, String waterLocation, String waterCondition, int virusPPM, int contaminantPPM){
        this.dateTime = dateTime;
        this.reportNum = reportNum;
        this.workerName = workerName;
        this.waterLocation = waterLocation;
        this.waterCondition = waterCondition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }

}
