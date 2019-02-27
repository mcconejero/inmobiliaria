package com.triana.salesianos.inmobilimario.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.triana.salesianos.inmobilimario.models.PostResponse;
import com.triana.salesianos.inmobilimario.models.ResponseContainer;
import com.triana.salesianos.inmobilimario.retrofit.generator.ServiceGenerator;
import com.triana.salesianos.inmobilimario.retrofit.services.PostService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends AndroidViewModel {

        private MutableLiveData<List<PostResponse>> listPosts = new MutableLiveData<List<PostResponse>>();
        private MutableLiveData<PostResponse> post = new MutableLiveData<PostResponse>();
        private PostService service;

        public PostViewModel(@NonNull Application application) {
            super(application);
            getAllPosts();
        }

        /*public void getPostDeails() {
            service = ServiceGenerator.createService(PostService.class);
            Call<ResponseContainer<PostResponse>> call = service.onePost();
            call.enqueue(new Callback<ResponseContainer<PostResponse>>() {
                @Override
                public void onResponse(Call<ResponseContainer<PostResponse>> call, Response<ResponseContainer<PostResponse>> response) {
                    try {
                        ResponseContainer<PostResponse> data = response.body();
                        post.setValue(data.getRows());

                    } catch (Exception e) {
                        Log.e("");
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<PostResponse>> call, Throwable t) {

                }
            });
        }*/

        public void getAllPosts() {
            service = ServiceGenerator.createService(PostService.class);
            Call<ResponseContainer<PostResponse>> call = service.getListPost();
            call.enqueue(new Callback<ResponseContainer<PostResponse>>() {
                @Override
                public void onResponse(Call<ResponseContainer<PostResponse>> call, Response<ResponseContainer<PostResponse>> response) {
                    try {
                        ResponseContainer<PostResponse> data = response.body();
                        listPosts.setValue(data.getRows());
                    } catch (Exception e) {
                        Log.d("onResponse", "Error here");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<PostResponse>> call, Throwable t) {
                    Log.e("NetworkError", t.getMessage());
                }
            });
        }

        public MutableLiveData<List<PostResponse>> getListPosts() {
            return listPosts;
        }
    }