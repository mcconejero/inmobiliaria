package com.triana.salesianos.inmobilimario.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.triana.salesianos.inmobilimario.R;
import com.triana.salesianos.inmobilimario.models.PostResponse;

import java.util.List;


public class MyPostRecyclerViewAdapter extends RecyclerView.Adapter<MyPostRecyclerViewAdapter.ViewHolder> {

    private List<PostResponse> mValues;
    private final PostInteractionListener mListener;
    private Context ctx;

    public MyPostRecyclerViewAdapter(Context ctx, List<PostResponse> items, PostInteractionListener listener, int layout) {
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
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mRooms.setText(String.valueOf(mValues.get(position).getRooms()));
        holder.mPrice.setText(String.valueOf(mValues.get(position).getPrice()));
        holder.mProvince.setText(mValues.get(position).getProvince());

        if (holder.mItem.getPhotos() != null) {
            Glide
                    .with(ctx)
                    .load(holder.mItem.getPhotos()[0])
                    .into(holder.mPicture);
        }
        holder.itemView.setTag(mValues.get(position));
        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostResponse item = (PostResponse) v.getTag();
                Intent intent = new Intent(ctx, PostDetailActivity.class);
                intent.putExtra(PostDetailActivity.ARG_ITEM_ID, item.getId());
                ctx.startActivity(intent);
            }
        });*/
    }

    public void setNewPosts(List<PostResponse> posts){
        this.mValues = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mRooms;
        public final TextView mPrice;
        public final TextView mProvince;
        public final ImageView mPicture;
        public PostResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = view.findViewById(R.id.title);
            mRooms = view.findViewById(R.id.rooms);
            mPrice = view.findViewById(R.id.price);
            mProvince = view.findViewById(R.id.province);
            mPicture = view.findViewById(R.id.picture);

        }
    }
}
