package com.gridyn.potspot.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.databinding.ItemFriendBinding;
import com.gridyn.potspot.interfaces.SelectFriendsInterface;
import com.gridyn.potspot.model.FriendModel;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.utils.ServerApiUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dmytro_vodnik on 8/28/16.
 * working on potspot project
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{

    private static final String TAG = FriendsAdapter.class.getName();
    private final SelectFriendsInterface selectFriendsInterface;
    private final int partySize;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<FriendModel> friendModelArrayList;
    String bookingId;

    public FriendsAdapter(Context context, SelectFriendsInterface selectFriendsInterface, int partySize,
                          String bookingId) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        friendModelArrayList = new ArrayList<>();
        this.selectFriendsInterface = selectFriendsInterface;
        this.partySize = partySize;
        this.bookingId = bookingId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemFriendBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_friend, parent, true);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final FriendModel friendModel = getItem(position);

        holder.binding.setFriend(friendModel);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: clicked friend = " + friendModel);

                int selectedCount = getSelectedCount();

                if (selectedCount < partySize) {
                    friendModel.setSelected(!friendModel.isSelected());

                    selectFriendsInterface.selectedFriends(getSelectedCount());
                } else if (friendModel.isSelected()) {
                    friendModel.setSelected(false);
                } else {
                    Toast.makeText(context, R.string.party_size_alert, Toast.LENGTH_SHORT).show();
                }

                if (!friendModel.isSelected() && friendModel.isInviteSent()) {

                    Call<SuccessResponse> call = ServerApiUtil.initUser()
                            .removeFriendFromBooking(bookingId, friendModel.getId(), Person.getTokenMap());

                    call.enqueue(new Callback<SuccessResponse>() {
                        @Override
                        public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                            SuccessResponse successResponse = response.body();

                            if (successResponse == null) {

                            } else {

                                if (successResponse.isSuccess())
                                    friendModel.setInviteSent(false);
                            }

                        }

                        @Override
                        public void onFailure(Throwable t) {

                            Log.e(TAG, "onFailure: error while remove friend = " + Log.getStackTraceString(t));
                        }
                    });
                }
            }
        });

        //TODO load pic here
    }

    public FriendModel getItem(int position) {

        return friendModelArrayList.get(position);
    }

    public void addAll(List<FriendModel> list) {

        this.friendModelArrayList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return friendModelArrayList.size();
    }

    public ArrayList<FriendModel> getSelectedItems() {

        ArrayList<FriendModel> selectedItems = new ArrayList<>();

        for (FriendModel friendModel : friendModelArrayList)
            if (friendModel.isSelected())
                selectedItems.add(friendModel);

        return selectedItems;
    }

    public int getSelectedCount() {

        int selectedCount = 0;

        for (FriendModel friendModel : friendModelArrayList)
            if (friendModel.isSelected())
                selectedCount++;

        return selectedCount;
    }

    public ArrayList<FriendModel> getItems() {
        return friendModelArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemFriendBinding binding;
        ImageView pic;

        public ViewHolder(ItemFriendBinding itemView) {
            super(itemView.getRoot());

            this.binding = itemView;
            this.pic = (ImageView) binding.getRoot().findViewById(R.id.friend_pic);
        }
    }
}
