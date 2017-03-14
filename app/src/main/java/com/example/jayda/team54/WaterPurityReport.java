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
