package com.triana.salesianos.inmobilimario.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.triana.salesianos.inmobilimario.R;
import com.triana.salesianos.inmobilimario.models.User;

public class MyProfileRecyclerViewAdapter extends RecyclerView.Adapter<MyProfileRecyclerViewAdapter.ViewHolder>{

    private User mUser;
    private final ProfileInteractionListener mListener;
    private Context ctx;

    public MyProfileRecyclerViewAdapter(Context ctx, User items, ProfileInteractionListener listener, int layout) {
        mUser = items;
        mListener = listener;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mUser;
        holder.mName.setText(mUser.getName());
        holder.mEmail.setText(mUser.getName());
        holder.mFavourites.setText(mUser.getName());
        holder.mMyPosts.setText(mUser.getName());
        holder.mLogOut.setText(mUser.getName());
        holder.mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (holder.mItem.getPicture() != null) {
            Glide
                    .with(ctx)
                    .load(holder.mItem.getPicture())
                    .into(holder.mPicture);
        }
        holder.itemView.setTag(mUser);

        /*
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostResponse item = (PostResponse) v.getTag();
                Intent intent = new Intent(ctx, PostDetailActivity.class);
                intent.putExtra(PostDetailActivity.ARG_ITEM_ID, item.getId());
                ctx.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final TextView mEmail;
        public final Button mFavourites;
        public final Button mMyPosts;
        public final Button mLogOut;
        public final Button mChange;
        public final ImageView mPicture;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.tvName);
            mChange = view.findViewById(R.id.btnChangePicture);
            mEmail = view.findViewById(R.id.tvEmail);
            mFavourites = view.findViewById(R.id.tvFav);
            mMyPosts = view.findViewById(R.id.tvMyPosts);
            mLogOut = view.findViewById(R.id.btnLogOut);
            mPicture = view.findViewById(R.id.picture);

        }
    }

}
