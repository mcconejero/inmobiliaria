package com.triana.salesianos.inmobilimario.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.triana.salesianos.inmobilimario.R;
import com.triana.salesianos.inmobilimario.UtilToken;
import com.triana.salesianos.inmobilimario.models.PostResponse;
import com.triana.salesianos.inmobilimario.models.ResponseContainer;
import com.triana.salesianos.inmobilimario.retrofit.generator.ServiceGenerator;
import com.triana.salesianos.inmobilimario.retrofit.generator.TipoAutenticacion;
import com.triana.salesianos.inmobilimario.retrofit.services.PostService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link PostInteractionListener}
 * interface.
 */
public class PostFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private PostInteractionListener mListener;
    private RecyclerView recyclerView;
    private List<PostResponse> postList;
    private MyPostRecyclerViewAdapter adapter;

    public PostFragment() {
    }

    // TODO: Customize parameter initialization
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

        // Set the adapter
            if (view instanceof SwipeRefreshLayout) {
            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.listPosts);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
                postList = new ArrayList<>();

                /**
                 * RELLENAR inmuebleList
                 */
                PostService service = ServiceGenerator.createService(PostService.class);
                Call<ResponseContainer<PostResponse>> call = service.getListPost();

                call.enqueue(new Callback<ResponseContainer<PostResponse>>() {

                    @Override
                    public void onResponse(Call<ResponseContainer<PostResponse>> call, Response<ResponseContainer<PostResponse>> response) {
                        if (response.code() != 200) {
                            Toast.makeText(getActivity(), "Error en petición", Toast.LENGTH_SHORT).show();
                        } else {
                            postList = response.body().getRows();

                            adapter = new MyPostRecyclerViewAdapter(
                                    ctx,
                                    postList,
                                    mListener
                            );
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
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PostInteractionListener) {
            mListener = (PostInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
