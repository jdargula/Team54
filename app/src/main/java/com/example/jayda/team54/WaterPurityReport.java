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

    public static final int MAX_LAT = 90;
    public static final int MAX_LONG = 180;

    public String getDateTime() { return dateTime; }
    public int getReportNum() { return  reportNum; }
    public String getWorkerName() { return workerName; }
    public String getWaterLocation() { return waterLocation; }
    public String getWaterCondition() { return  waterCondition; }
    public int getVirusPPM() { return virusPPM; }
    public int getContaminantPPM() { return  contaminantPPM; }

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
