package com.triana.salesianos.inmobilimario.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.triana.salesianos.inmobilimario.R;
import com.triana.salesianos.inmobilimario.UtilToken;
import com.triana.salesianos.inmobilimario.models.PostResponse;
import com.triana.salesianos.inmobilimario.retrofit.services.PostService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostRecyclerViewAdapter extends RecyclerView.Adapter<MyPostRecyclerViewAdapter.ViewHolder> {

    private final List<PostResponse> mValues;
    private final PostFragment.OnListFragmentInteractionListener mListener;
    private Context ctx;
    PostService service;

    public MyPostRecyclerViewAdapter(Context ctx, List<PostResponse> items, PostFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String jwt = UtilToken.getToken(ctx);
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mPrice.setText((int) mValues.get(position).getPrice());
        holder.mRooms.setText(mValues.get(position).getRooms());
        holder.mProvince.setText(mValues.get(position).getProvince());

        if (holder.mItem.getPhotos().size() != 0) {
            Glide.with(holder.mView).load(holder.mItem.getPhotos().get(0))
                    .centerCrop()
                    .into(holder.mPicture);
        } else {
            Glide.with(holder.mView).load("https://rexdalehyundai.ca/dist/img/nophoto.jpg")
                    .centerCrop()
                    .into(holder.mPicture);
        }
        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
        //poner los dos iconos uno encima de otro y lo que hay que hacer es setear la visibilidad true o false depende
        holder.fav.setOnClickListener(v -> {
            int c = 0;

            if (c == 0) {

                if (jwt == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                    builder.setTitle(R.string.title_add).setMessage(R.string.message_add);
                    builder.setPositiveButton(R.string.go, (dialog, which) ->
                            contexto.startActivity(new Intent(contexto, LoginActivity.class)));
                    builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
                        Log.d("Back", "Going back");
                    });
                    AlertDialog dialog = builder.create();

                    dialog.show();
                } else {
                    service = ServiceGenerator.createService(PropertyService.class, jwt, AuthType.JWT);

                    Call<PropertyResponse> call = service.addFav(holder.mItem.getId());
                    call.enqueue(new Callback<PropertyResponse>() {
                        @Override
                        public void onResponse(Call<PropertyResponse> call, Response<PropertyResponse> response) {
                            if (response.code() != 201) {
                                Toast.makeText(contexto, "Error in request", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(contexto, "Added to favourites", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PropertyResponse> call, Throwable t) {
                            Toast.makeText(contexto, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                c = 1;
                holder.fav.setImageResource(R.drawable.ic_fav);
            } else {
                service = ServiceGenerator.createService(PropertyService.class, jwt, AuthType.JWT);

                Call<PropertyResponse> call = service.deleteFav(holder.mItem.getId());
                call.enqueue(new Callback<PropertyResponse>() {
                    @Override
                    public void onResponse(Call<PropertyResponse> call, Response<PropertyResponse> response) {
                        if (response.code() != 200) {
//                            Toast.makeText(contexto, "Error in request", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(contexto, "Deleted from favourites", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PropertyResponse> call, Throwable t) {
//                        Toast.makeText(contexto, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            c = 0;
            holder.fav.setImageResource(R.drawable.ic_no_fav);

        });

        holder.constraintLayout.setOnClickListener(v -> {
            System.out.println(holder.mItem.getId());
            service = ServiceGenerator.createService(PropertyService.class);
            Call<ResponseContainerOneRow<PropertyResponse>> callOne = service.getOne(holder.mItem.getId());
            callOne.enqueue(new Callback<ResponseContainerOneRow<PropertyResponse>>() {
                @Override
                public void onResponse(Call<ResponseContainerOneRow<PropertyResponse>> call, Response<ResponseContainerOneRow<PropertyResponse>> response) {
                    PropertyResponse resp = response.body().getRows();
                    Intent detailsActivity = new Intent(contexto , DetailsActivity.class);
                    detailsActivity.putExtra("property", resp);
                    contexto.startActivity(detailsActivity);
                }

                @Override
                public void onFailure(Call<ResponseContainerOneRow<PropertyResponse>> call, Throwable t) {

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
        public final TextView mDescription;
        public final TextView mRooms;
        public final TextView mPrice;
        public final TextView mProvince;
        public final TextView mPicture;
        public PostResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = view.findViewById(R.id.title);
            mDescription = view.findViewById(R.id.description);
            mRooms = view.findViewById(R.id.rooms);
            mPrice = view.findViewById(R.id.price);
            mProvince = view.findViewById(R.id.province);
            mPicture = view.findViewById(R.id.photo);
        }
    }
}
