
package com.evthai.model.stations;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class StationResponce {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("numberOfStation")
    @Expose
    private Integer numberOfStation;
    @SerializedName("stations")
    @Expose
    private List<Station> stations = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getNumberOfStation() {
        return numberOfStation;
    }

    public void setNumberOfStation(Integer numberOfStation) {
        this.numberOfStation = numberOfStation;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

}
