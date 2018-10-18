package com.evthai.model.charger;

public class ChargerRequests {

    private int siteId;

    public ChargerRequests(int siteId) {
        this.siteId = siteId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}
