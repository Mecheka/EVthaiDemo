package com.evthai.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.ArrayList;

@Parcel
public class StationColaction {

    @SerializedName("status")
    private String status;
    @SerializedName("infos")
    private ArrayList<InfoStationModel> intosList;

    public StationColaction() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<InfoStationModel> getIntosList() {
        return intosList;
    }

    public void setIntosList(ArrayList<InfoStationModel> intosList) {
        this.intosList = intosList;
    }
}
