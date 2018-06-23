package com.evthai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class InfoStationModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("detail")
    @Expose
    private DetailModel detail;
    @SerializedName("statusId")
    @Expose
    private int statusId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("location")
    @Expose
    private LocationModel location;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;

    @ParcelConstructor
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

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
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
