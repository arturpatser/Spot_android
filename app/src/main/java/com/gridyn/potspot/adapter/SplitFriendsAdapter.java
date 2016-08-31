package com.gridyn.potspot.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gridyn.potspot.R;
import com.gridyn.potspot.databinding.ItemSplitFriendBinding;
import com.gridyn.potspot.interfaces.BuySpotInterface;
import com.gridyn.potspot.model.FriendModel;

import java.util.ArrayList;

/**
 * Created by dmytro_vodnik on 8/28/16.
 * working on potspot project
 */
public class SplitFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<FriendModel> arrayList;
    private static final int FOOTER_ITEM = 1;
    private static final int FRIEND_ITEM = 0;
    BuySpotInterface buySpotInterface;

    public SplitFriendsAdapter(Context context, BuySpotInterface buySpotInterface) {

        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<>();
        this.buySpotInterface = buySpotInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {


            case FRIEND_ITEM:

                ItemSplitFriendBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_split_friend, parent, true);

                return new ViewHolder(binding);

            case FOOTER_ITEM:

                TextView textView = (TextView) layoutInflater.inflate(R.layout.more_friends, parent, false);

                return new FooterHolder(textView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {

            FriendModel friendModel = getItem(position);

            ((ViewHolder)holder).binding.setFriend(friendModel);
        }

        if (holder instanceof FooterHolder) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    buySpotInterface.showSplitFriends();
                }
            });
        }
    }

    private FriendModel getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getItemViewType(int position) {

        if (position == arrayList.size())
            return FOOTER_ITEM;

        return FRIEND_ITEM;
    }

    @Override
    public int getItemCount() {
        return arrayList.size() + 1;
    }

    public void addItems(ArrayList<FriendModel> arr) {

        this.arrayList.addAll(arr);
        notifyDataSetChanged();
    }

    public ArrayList<FriendModel> getItems() {
        return arrayList;
    }

    public void clear() {
        arrayList.clear();
        notifyDataSetChanged();
    }

    public void updateItem(String userId, boolean success) {

        for (FriendModel friendModel : arrayList) {

            if (friendModel.getId().equals(userId))
                friendModel.setAcceptedInvite(success);
        }

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemSplitFriendBinding binding;

        public ViewHolder(ItemSplitFriendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class FooterHolder extends RecyclerView.ViewHolder {
        public FooterHolder(TextView textView) {
            super(textView);
        }
    }
}
