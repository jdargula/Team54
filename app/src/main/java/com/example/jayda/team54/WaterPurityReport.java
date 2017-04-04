package com.example.jayda.team54;

/**
 * Information submitted for water purity reports.
 */

@SuppressWarnings("FieldCanBeLocal")
class WaterPurityReport {

    private final String dateTime;
    private final int reportNum;
    private final String workerName;
    private final String waterLocation;
    private final String waterCondition;
    private final int virusPPM;
    private final int contaminantPPM;

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
