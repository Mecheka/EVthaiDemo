package com.evthai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailModel {

    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("connector")
    @Expose
    private ConectorModel conector;

    public DetailModel(){
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ConectorModel getConector() {
        return conector;
    }

    public void setConector(ConectorModel conector) {
        this.conector = conector;
    }
}
