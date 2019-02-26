package com.triana.salesianos.inmobilimario.retrofit.services;

import com.triana.salesianos.inmobilimario.models.PostResponse;
import com.triana.salesianos.inmobilimario.models.ResponseContainer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostService {

    @GET("properties")
    Call<ResponseContainer<PostResponse>> getListPost();

    @GET("properties/{id}")
    Call<PostResponse> onePost(@Path("id") String id);

    @DELETE("properties/{id}")
    Call<ResponseBody> deletePost(@Path("id") String id);


}
