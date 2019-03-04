package com.triana.salesianos.inmobilimario.retrofit.services;

import com.triana.salesianos.inmobilimario.models.FavsResponse;
import com.triana.salesianos.inmobilimario.models.PostResponse;
import com.triana.salesianos.inmobilimario.models.ResponseContainer;
import com.triana.salesianos.inmobilimario.models.ResponseContainerTwo;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PostService {

    @GET("properties")
    Call<ResponseContainer<PostResponse>> getListPost(@QueryMap Map<String, String> option);

    @GET("properties/auth")
    Call<ResponseContainer<PostResponse>> listPostAuth(@QueryMap Map<String, String> options);

    @GET("properties")
    Call<ResponseContainer<PostResponse>> listGeo(@Query("near") String near);

    @GET("properties/mine")
    Call<ResponseContainer<FavsResponse>> getMine();

    @GET("properties/fav")
    Call<ResponseContainer<PostResponse>> getFavs();

    @GET("properties/{id}")
    Call<ResponseContainerTwo<PostResponse>> getOne(@Path("id") String id);

    @POST("properties")
    Call<PostResponse> create(@Body PostResponse property);

    @POST("properties/fav/{id}")
    Call<PostResponse> addFav (@Path("id") String id);

    /*@PUT("properties/{id}")
    Call<EditPropertyDto> edit(@Path("id") String id, @Body EditPropertyDto edited);*/

    @DELETE("properties/{id}")
    Call<PostResponse> delete(@Path("id") String id);

    @DELETE("properties/fav/{id}")
    Call<PostResponse> deleteFav(@Path("id") String id);

    @GET("properties/{id}")
    Call<PostResponse> onePost(@Path("id") String id);

    @DELETE("properties/{id}")
    Call<ResponseBody> deletePost(@Path("id") String id);

}
