package com.evthai.model.charger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class ChargerResponce {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("numberOfCharger")
    @Expose
    private Integer numberOfCharger;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("chargers")
    @Expose
    private List<Charger> chargers = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumberOfCharger() {
        return numberOfCharger;
    }

    public void setNumberOfCharger(Integer numberOfCharger) {
        this.numberOfCharger = numberOfCharger;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<Charger> getChargers() {
        return chargers;
    }

    public void setChargers(List<Charger> chargers) {
        this.chargers = chargers;
    }
}
