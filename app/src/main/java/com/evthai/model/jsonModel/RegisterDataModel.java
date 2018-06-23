package com.evthai.model.jsonModel;

public class RegisterDataModel {

    private String key;
    private String name;
    private String lastName;
    private String email;
    private String mobileNo;

    public RegisterDataModel(String key, String name, String lastName, String email, String mobileNo) {
        this.key = key;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.mobileNo = mobileNo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
