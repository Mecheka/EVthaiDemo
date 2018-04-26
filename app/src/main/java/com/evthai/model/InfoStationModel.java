package com.evthai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoStationModel {

    @SerializedName("id")
    private String id;
    @SerializedName("detail")
    private DetailModel detail;
    @SerializedName("status")
    private String status;
    @SerializedName("location")
    private LocationModel location;
    @SerializedName("remark")
    private String remark;
    @SerializedName("lastUpdate")
    private String lastUpdate;

    public InfoStationModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DetailModel getDetail() {
        return detail;
    }

    public void setDetail(DetailModel detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
