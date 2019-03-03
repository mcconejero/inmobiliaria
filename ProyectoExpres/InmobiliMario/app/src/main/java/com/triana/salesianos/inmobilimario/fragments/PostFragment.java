package com.triana.salesianos.inmobilimario.fragments;

<<<<<<< HEAD
import android.Manifest;
import android.annotation.SuppressLint;
=======
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
<<<<<<< HEAD
import android.support.v4.app.ActivityCompat;
=======
import android.support.annotation.Nullable;
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

<<<<<<< HEAD
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.triana.salesianos.inmobilimario.models.PostResponse;
=======
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301
import com.triana.salesianos.inmobilimario.R;
import com.triana.salesianos.inmobilimario.UtilToken;
import com.triana.salesianos.inmobilimario.models.PostResponse;
import com.triana.salesianos.inmobilimario.models.ResponseContainer;
import com.triana.salesianos.inmobilimario.retrofit.generator.AuthType;
import com.triana.salesianos.inmobilimario.retrofit.generator.ServiceGenerator;
import com.triana.salesianos.inmobilimario.retrofit.services.PostService;
import com.triana.salesianos.inmobilimario.viewmodel.PostViewModel;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private RecyclerView recyclerView;
<<<<<<< HEAD
    private OnListFragmentInteractionListener mListener;
    Context ctx;
    List<PostResponse> properties = new ArrayList<>();
    String jwt;
    PostService service;
    MyPostRecyclerViewAdapter adapter;
    Map<String, String> options = new HashMap<>();
    FusedLocationProviderClient locationClient;
=======
    private List<PostResponse> postList;
    private MyPostRecyclerViewAdapter adapter;
    private Context ctx;
    private PostViewModel viewModel;
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301

    public PostFragment() {
    }

    @SuppressLint("ValidFragment")
    public PostFragment(Map<String,String> options) {
        this.options = options;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PostFragment newInstance(int columnCount) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

<<<<<<< HEAD
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            jwt = UtilToken.getToken(view.getContext());
            options.put("near", "-6.0071807999999995,37.3803677");
            options.put("max_distance","1000000000000");
            RecyclerView recyclerView = (RecyclerView) view;
=======
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.listPosts);
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
<<<<<<< HEAD
            System.out.println(getCurrentLocation(context));

            if (jwt == null) {
                service = ServiceGenerator.createService(PostService.class);

                Call<ResponseContainer<PostResponse>> call = service.listPost(options);
                call.enqueue(new Callback<ResponseContainer<PostResponse>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<PostResponse>> call, Response<ResponseContainer<PostResponse>> response) {
                        if (response.code() != 200) {
                            Toast.makeText(getActivity(), "Error in request", Toast.LENGTH_SHORT).show();
                        } else {
                            properties = response.body().getRows();

                            adapter = new MyPostRecyclerViewAdapter(context, properties, mListener);
                            recyclerView.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseContainer<PostResponse>> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                service = ServiceGenerator.createService(PostService.class, jwt, AuthType.JWT);

                Call<ResponseContainer<PostResponse>> call = service.listPostAuth(options);
                call.enqueue(new Callback<ResponseContainer<PostResponse>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<PostResponse>> call, Response<ResponseContainer<PostResponse>> response) {
                        if (response.code() != 200) {
                            Toast.makeText(getActivity(), "Error in request", Toast.LENGTH_SHORT).show();
                        } else {
                            properties = response.body().getRows();

                            adapter = new MyPostRecyclerViewAdapter(context, properties, mListener);
                            recyclerView.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseContainer<PostResponse>> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
=======
            postList = new ArrayList<>();
            adapter = new MyPostRecyclerViewAdapter(getActivity(), postList, mListener, R.layout.fragment_post);
            recyclerView.setAdapter(adapter);
            throwViewModel();
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301
        }

        return view;
}

    private void throwViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(PostViewModel.class);
        viewModel.getListPosts().observe(getActivity(), new Observer<List<PostResponse>>() {
            @Override
            public void onChanged(@Nullable List<PostResponse> posts) {
                adapter.setNewPosts(posts);
            }
        });
    }

    @Override
    public void onAttach (Context context){
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach () {
        super.onDetach();
        mListener = null;
    }

<<<<<<< HEAD
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(PostResponse item);
    }

    public String getCurrentLocation(Context context) {
        final String[] currentLoc = new String[1];
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        locationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            // convierto la cadena a longitud + latitud
                            currentLoc[0] = location.getLongitude() + "," + location.getLatitude();
                        }
                    }
                });

        return currentLoc[0];
    }

=======
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301
}
