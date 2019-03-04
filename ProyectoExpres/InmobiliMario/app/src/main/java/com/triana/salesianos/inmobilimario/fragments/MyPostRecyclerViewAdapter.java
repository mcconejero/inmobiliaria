package com.triana.salesianos.inmobilimario.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.triana.salesianos.inmobilimario.R;
import com.triana.salesianos.inmobilimario.UtilToken;
import com.triana.salesianos.inmobilimario.activities.DetailsActivity;
import com.triana.salesianos.inmobilimario.activities.LoginActivity;
import com.triana.salesianos.inmobilimario.models.PhotoResponse;
import com.triana.salesianos.inmobilimario.models.PostResponse;
import com.triana.salesianos.inmobilimario.models.ResponseContainerTwo;
import com.triana.salesianos.inmobilimario.retrofit.generator.AuthType;
import com.triana.salesianos.inmobilimario.retrofit.generator.ServiceGenerator;
import com.triana.salesianos.inmobilimario.retrofit.services.PostService;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostRecyclerViewAdapter extends RecyclerView.Adapter<MyPostRecyclerViewAdapter.ViewHolder> {

    private PhotoResponse photo;
    private final List<PostResponse> mValues;
    private final PostFragment.OnListFragmentInteractionListener mListener;
    Context ctx;
    PostService service;

    public MyPostRecyclerViewAdapter(Context context, List<PostResponse> items, PostFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String jwt = UtilToken.getToken(ctx);
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        //holder.mPrice.setText(Math.round(mValues.get(position).getPrice()));
        //holder.mSize.setText(Math.round(mValues.get(position).getSize()));
        holder.mCity.setText(mValues.get(position).getCity());

        if (holder.mItem.getPhotos().size() != 0) {
            Glide.with(holder.mView).load(holder.mItem.getPhotos().get(0))
                    .centerCrop()
                    .into(holder.mPhoto);
        } else {
            Glide.with(holder.mView).load("https://rexdalehyundai.ca/dist/img/nophoto.jpg")
                    .centerCrop()
                    .into(holder.mPhoto);
        }
        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });

        holder.mFav.setOnClickListener(v -> {


            if (jwt == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Add to favourites").setMessage("Do you want to add to favorites?");
                builder.setPositiveButton("Ok", (dialog, which) ->
                        ctx.startActivity(new Intent(ctx, LoginActivity.class)));
                builder.setNegativeButton("Cancel", (dialog, id) -> {
                    Log.d("Back", "Going back");
                });
                AlertDialog dialog = builder.create();

                dialog.show();

            } else {

                service = ServiceGenerator.createService(PostService.class, jwt, AuthType.JWT);

                if (holder.mFav.isEnabled() == true){

                    Call<PostResponse> call = service.addFav(holder.mItem.getId());
                    call.enqueue(new Callback<PostResponse>() {
                    @Override
                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(ctx, "Error in request", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ctx, "Added to favourites", Toast.LENGTH_LONG).show();
                            holder.mFav.setImageResource(R.drawable.ic_full_fav);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostResponse> call, Throwable t) {
                        Toast.makeText(ctx, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });

                }else{

                Call<PostResponse> call = service.deleteFav(holder.mItem.getId());
                call.enqueue(new Callback<PostResponse>() {
                    @Override
                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                        if (response.code() != 200) {
                            Toast.makeText(ctx, "Error in request", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ctx, "Deleted from favourites", Toast.LENGTH_LONG).show();
                            holder.mFav.setImageResource(R.drawable.ic_empty_fav);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostResponse> call, Throwable t) {
                    }
                });
            }
        }
        });

        holder.mConstraintLayout.setOnClickListener(v -> {
            System.out.println(holder.mItem.getId());
            service = ServiceGenerator.createService(PostService.class);
            Call<ResponseContainerTwo<PostResponse>> callOne = service.getOne(holder.mItem.getId());
            callOne.enqueue(new Callback<ResponseContainerTwo<PostResponse>>() {

                @Override
                public void onResponse(Call<ResponseContainerTwo<PostResponse>> call, Response<ResponseContainerTwo<PostResponse>> response) {
                    PostResponse resp = response.body().getRows();
                    Intent detailsActivity = new Intent(ctx , DetailsActivity.class);
                    detailsActivity.putExtra("post", resp);
                    ctx.startActivity(detailsActivity);
                }

                @Override
                public void onFailure(Call<ResponseContainerTwo<PostResponse>> call, Throwable t) {

                }
            });
        });

    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mPrice;
        public final TextView mSize;
        public final TextView mCity;
        public final ImageView mPhoto;
        public final ImageView mFav;
        public PostResponse mItem;
        public ConstraintLayout mConstraintLayout;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = view.findViewById(R.id.titlePost);
            mPrice = view.findViewById(R.id.pricePost);
            mSize = view.findViewById(R.id.sizePost);
            mCity = view.findViewById(R.id.cityPost);
            mPhoto = view.findViewById(R.id.picturePost);
            mFav = view.findViewById(R.id.favPost);
            mConstraintLayout = view.findViewById(R.id.constraint);
        }

    }
}
