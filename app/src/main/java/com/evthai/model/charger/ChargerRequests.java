package com.evthai.model.charger;

public class ChargerRequests {
    private String siteId;

    public ChargerRequests(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
