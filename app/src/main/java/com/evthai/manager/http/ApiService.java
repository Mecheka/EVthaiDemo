package com.evthai.manager.http;

import com.evthai.model.StationColaction;
import com.evthai.model.jsonModel.LoginDataModel;
import com.evthai.model.jsonModel.RegisterDataModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("v1/charger/")
    Call<StationColaction> loadStation();

    @POST("dev/login/")
    Call<ResponseBody> loginRequest(@Body LoginDataModel login);

    @POST("dev/register/")
    Call<ResponseBody> registerRequest(@Body RegisterDataModel regis);

}
