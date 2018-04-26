package com.evthai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConectorModel {

    @SerializedName("no1")
    @Expose
    private String no1;
    @SerializedName("no2")
    @Expose
    private String no2;

    public ConectorModel(){

    }

    public String getNo1() {
        return no1;
    }

    public void setNo1(String no1) {
        this.no1 = no1;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

}
