package com.triana.salesianos.inmobilimario.retrofit.services;

import com.triana.salesianos.inmobilimario.models.ResponseContainer;
import com.triana.salesianos.inmobilimario.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OtherService {

    @GET("/users")
    Call<ResponseContainer<User>> listUsers();

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") Long id);

}
