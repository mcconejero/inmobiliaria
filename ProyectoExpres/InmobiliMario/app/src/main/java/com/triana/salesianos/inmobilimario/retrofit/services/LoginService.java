package com.triana.salesianos.inmobilimario.retrofit.services;

import com.triana.salesianos.inmobilimario.models.LoginResponse;
import com.triana.salesianos.inmobilimario.models.SignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/auth")
    Call<LoginResponse> doLogin(@Header("Authorization") String authorization);

    @POST("/users")
    Call<LoginResponse> doSignUp(@Body SignUp signup);

}
