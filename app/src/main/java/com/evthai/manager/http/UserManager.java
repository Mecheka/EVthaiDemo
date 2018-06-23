package com.evthai.manager.http;

import android.content.Context;

public class UserManager {

    private static UserManager inInstance;

    public static UserManager getInstance() {
        if (inInstance == null){
            inInstance = new UserManager();
        }
        return inInstance;
    }

    private String token;
    private Context mContext;

    private UserManager() {

        mContext = Contextor.getInstance().getContext();

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
