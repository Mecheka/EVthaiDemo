package com.evthai.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class MetterModel {

     private String nozzle;
     private boolean metterStatus;
     private String cardNo;
     private String startChart;
     private String endChart;
     private String endMetter;

    @ParcelConstructor
    public MetterModel(String nozzle, boolean metterStatus, String cardNo, String startChart, String endChart, String endMetter) {
        this.nozzle = nozzle;
        this.metterStatus = metterStatus;
        this.cardNo = cardNo;
        this.startChart = startChart;
        this.endChart = endChart;
        this.endMetter = endMetter;
    }

    public String getNozzle() {
        return nozzle;
    }

    public void setNozzle(String nozzle) {
        this.nozzle = nozzle;
    }

    public boolean isMetterStatus() {
        return metterStatus;
    }

    public void setMetterStatus(boolean metterStatus) {
        this.metterStatus = metterStatus;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getStartChart() {
        return startChart;
    }

    public void setStartChart(String startChart) {
        this.startChart = startChart;
    }

    public String getEndChart() {
        return endChart;
    }

    public void setEndChart(String endChart) {
        this.endChart = endChart;
    }

    public String getEndMetter() {
        return endMetter;
    }

    public void setEndMetter(String endMetter) {
        this.endMetter = endMetter;
    }
}
