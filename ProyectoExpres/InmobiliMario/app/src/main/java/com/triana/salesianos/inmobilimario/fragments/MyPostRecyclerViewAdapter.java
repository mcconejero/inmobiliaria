package com.triana.salesianos.inmobilimario.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.triana.salesianos.inmobilimario.models.PostResponse;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PostResponse} and makes a call to the
 * specified {@link PostInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */

public class MyPostRecyclerViewAdapter extends RecyclerView.Adapter<MyPostRecyclerViewAdapter.ViewHolder> {

    private final List<PostResponse> mValues;
    private final PostInteractionListener mListener;
    private Context ctx;

    public MyPostRecyclerViewAdapter(Context ctx, List<PostResponse> items, PostInteractionListener listener) {
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
        holder.mDescription.setText(mValues.get(position).getDescription());
        holder.mRooms.setText(mValues.get(position).getRooms());
        holder.mPrice.setText((int) mValues.get(position).getPrice());
        holder.mProvince.setText(mValues.get(position).getProvince());

//        Glide
//                .with(ctx)
//                .load(mValues.get(position).getPicture())
//                .into(new SimpleTarget<Drawable>(){
//
//                    @Override
//                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                        holder.mPostPicture.setBackground(resource);
//                    }
//                });
        holder.itemView.setTag(mValues.get(position));
        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostResponse item = (PostResponse) v.getTag();
                Intent intent = new Intent(ctx, HuertoDetailActivity.class);
                intent.putExtra(HuertoDetailFragment.ARG_ITEM_ID, item.getId());
                ctx.startActivity(intent);
            }
        });*/
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
        public PostResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = view.findViewById(R.id.title);
            mDescription = view.findViewById(R.id.description);
            mRooms = view.findViewById(R.id.rooms);
            mPrice = view.findViewById(R.id.price);
            mProvince = view.findViewById(R.id.province);

        }
    }
}
