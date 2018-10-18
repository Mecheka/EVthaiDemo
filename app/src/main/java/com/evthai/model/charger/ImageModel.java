package com.evthai.model.charger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ImageModel {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
