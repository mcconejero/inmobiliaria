package com.triana.salesianos.inmobilimario.retrofit.services;

import com.triana.salesianos.inmobilimario.models.PhotoResponse;
import com.triana.salesianos.inmobilimario.models.PhotoUploadResponse;
import com.triana.salesianos.inmobilimario.models.ResponseContainer;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PhotoService {

    @GET("/photos")
    Call<ResponseContainer<PhotoResponse>> getAll();

    @GET("/photos/{id}")
    Call<PhotoResponse> getOne(@Path("id") String id);

    @Multipart
    @POST("/photos")
    Call<PhotoUploadResponse> upload(@Part MultipartBody.Part photo, @Part("propertyId") RequestBody propertyId);

    @DELETE("/photos/{id}")
    Call<PhotoResponse> delete(@Path("id") String id);

}
