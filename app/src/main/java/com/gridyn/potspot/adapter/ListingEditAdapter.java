package com.gridyn.potspot.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gridyn.potspot.BitmapHelper;
import com.gridyn.potspot.R;

import java.util.List;

public class ListingEditAdapter extends RecyclerView.Adapter<ListingEditAdapter.Holder> {

    private final List<String> mImages;
    private final Context mContext;

    public ListingEditAdapter(List<String> images, Context context) {
        mImages = images;
        mContext = context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_listing_edit, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.imageView.setImageDrawable(new BitmapDrawable(mContext.getResources(), BitmapHelper.decodeToBitmap(mImages.get(position))));

    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.list_edit_image);
        }
    }
}
