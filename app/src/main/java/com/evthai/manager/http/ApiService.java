package com.evthai.manager.http;

import com.evthai.model.StationColaction;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ApiService {

    @POST("charger/")
    Call<StationColaction> loadStation();

}
