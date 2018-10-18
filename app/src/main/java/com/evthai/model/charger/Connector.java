package com.evthai.model.charger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Connector {

    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cardNo")
    @Expose
    private String cardNo;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("meterValue")
    @Expose
    private Integer meterValue;
    @SerializedName("etf")
    @Expose
    private Integer etf;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getMeterValue() {
        return meterValue;
    }

    public void setMeterValue(Integer meterValue) {
        this.meterValue = meterValue;
    }

    public Integer getEtf() {
        return etf;
    }

    public void setEtf(Integer etf) {
        this.etf = etf;
    }

}
