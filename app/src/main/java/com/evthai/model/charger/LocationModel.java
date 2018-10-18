package com.evthai.model.charger;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class LocationModel {

    @SerializedName("station")
    private
    String station;
    @SerializedName("lat")
    private
    String lat;
    @SerializedName("long")
    private
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
