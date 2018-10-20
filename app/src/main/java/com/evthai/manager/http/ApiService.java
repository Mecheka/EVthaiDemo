package com.evthai.manager.http;

import com.evthai.model.charger.ChargerRequests;
import com.evthai.model.charger.ChargerResponce;
import com.evthai.model.jsonModel.LoginDataModel;
import com.evthai.model.jsonModel.RegisterDataModel;
import com.evthai.model.stations.StationResponce;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/v1/charger/")
    Call<ChargerResponce> loadCharger(@Body ChargerRequests charger);

    @POST("/api/v1/stations")
    Call<StationResponce> loadStation();

    @POST("/api/dev/login/")
    Call<ResponseBody> loginRequest(@Body LoginDataModel login);

    @POST("/api/dev/register/")
    Call<ResponseBody> registerRequest(@Body RegisterDataModel regis);

}
