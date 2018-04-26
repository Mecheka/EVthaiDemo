package com.evthai.model;

import com.google.gson.annotations.SerializedName;

public class LocationModel {

    @SerializedName("station")
    String station;
    @SerializedName("lat")
    String lat;
    @SerializedName("long")
    String lng;

    public LocationModel() {

    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
