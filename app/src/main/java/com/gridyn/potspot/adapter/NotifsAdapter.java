package com.gridyn.potspot.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.activity.BuySpotActivity;
import com.gridyn.potspot.databinding.ItemBookFriendInviteBinding;
import com.gridyn.potspot.databinding.ItemBookRequestBinding;
import com.gridyn.potspot.databinding.ItemBookVerifiedBinding;
import com.gridyn.potspot.model.notificationsModels.Message;
import com.gridyn.potspot.response.PaymentResponse;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.utils.ServerApiUtil;
import com.gridyn.potspot.utils.picassoTransform.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dmytro_vodnik on 9/12/16.
 * working on potspot project
 */
public class NotifsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = NotifsAdapter.class.getName();
    private static final int BOOK_VERIFIED = 0;
    private static final int BOOK_REQUEST = 1;
    private static final int BOOK_PENDING_VERIFY = 2;
    private static final int BOOK_INVITED = 3;
    LayoutInflater layoutInflater;
    Context context;
    List<Message> notifsList;
    private RecyclerView parent;
    private int avaSize = 96;

    public NotifsAdapter(Context context) {

        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.notifsList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == BOOK_VERIFIED) {

            ItemBookVerifiedBinding itemBookVerifiedBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.item_book_verified, parent, true);

            itemBookVerifiedBinding.setIsPending(false);

            return new BookVirifiedHolder(itemBookVerifiedBinding);
        }

        if (viewType == BOOK_REQUEST) {

            ItemBookRequestBinding itemBookRequestBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.item_book_request, parent, true);

            return new BookRequestHolder(itemBookRequestBinding);
        }

        if (viewType == BOOK_PENDING_VERIFY) {

            ItemBookVerifiedBinding itemBookVerifiedBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.item_book_verified, parent, true);

            itemBookVerifiedBinding.setIsPending(true);

            return new BookVirifiedHolder(itemBookVerifiedBinding);
        }

        if (viewType == BOOK_INVITED) {

            ItemBookFriendInviteBinding bookFriendInviteBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.item_book_friend_invite, parent, true);

            return new BookFriendInviteHolder(bookFriendInviteBinding);
        }

        if (viewType == -1)
            return new StubHolder(new View(context));

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final Message notif = getItem(position);
        
        final String requestId = notif.getId().get$id();

        if (holder instanceof BookVirifiedHolder) {

            ((BookVirifiedHolder) holder).binding.setSpotName(notif.getSpot().getData().getName());
            ((BookVirifiedHolder) holder).binding.setMessage(notif.getSpot().getData().getAddress());

            if (notif.getSpot().getData().getImgs().size() > 0)
            Picasso.with(context)
                    .load(Constant.URL_IMAGE + notif.getSpot().getData().getImgs().get(0))
                    .into(((BookVirifiedHolder) holder).spotImg);

            if (notif.getSpot().getData().getUserImgs().size() > 0)
            Picasso.with(context)
                    .load(Constant.URL_IMAGE + notif.getSpot().getData().getUserImgs().get(0))
                    .resize(avaSize,avaSize)
                    .transform(new CircleTransform(avaSize))
                    .into(((BookVirifiedHolder) holder).avatar);

            ((BookVirifiedHolder) holder).available.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!((BookVirifiedHolder) holder).binding.getIsPending()) {

                        Intent intent = new Intent(context, BuySpotActivity.class);
                        intent.putExtra("id", notif.getSpot().getId().get$id());
                        intent.putExtra(Constant.REQUEST_ID, notif.getId().get$id());
                        intent.putExtra(Constant.OPEN_FOR_BOOK, false);
                        intent.putExtra(Constant.PARTY_SIZE, String.valueOf(notif.getData().getGuests()));
                        intent.putExtra(Constant.SPOT_PRICE, notif.getSpot().getData().getPrice());
                        context.startActivity(intent);
                    } else
                        Snackbar.make(parent, context.getString(R.string.request_process), Snackbar.LENGTH_SHORT).show();
                }
            });

            ((BookVirifiedHolder) holder).remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<PaymentResponse> call = ServerApiUtil.initUser().cancelPaying(notif.getId().get$id(),
                            Person.getTokenMap());

                    call.enqueue(new Callback<PaymentResponse>() {
                        @Override
                        public void onResponse(Response<PaymentResponse> response, Retrofit retrofit) {

                            PaymentResponse paymentResponse = response.body();

                            if (paymentResponse.isSuccess()) {

                                removeItem(notif, position);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                            Log.e(TAG, "onFailure: error while cancel req = " + Log.getStackTraceString(t));

                            Snackbar.make(parent, R.string.error_connection, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        if (holder instanceof BookRequestHolder) {

            ((BookRequestHolder) holder).itemBookRequestBinding.setSpotName(notif.getSpot().getData().getName());

            if (notif.getSpot().getData().getImgs().size() > 0)
                Picasso.with(context)
                        .load(Constant.URL_IMAGE + notif.getSpot().getData().getImgs().get(0))
                        .into(((BookRequestHolder) holder).spotImg);

            ((BookRequestHolder) holder).acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<SuccessResponse> call = ServerApiUtil.initUser().confirmBooking(notif.getId().get$id(),
                            Person.getTokenMap());

                    call.enqueue(new Callback<SuccessResponse>() {
                        @Override
                        public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                            SuccessResponse success = response.body();

                            Log.d(TAG, "onResponse: resp = " + success);

                            if (success.isSuccess())
                                removeItem(notif, position);
                        }

                        @Override
                        public void onFailure(Throwable t) {

                            Log.e(TAG, "onFailure: error while accept request = " + Log.getStackTraceString(t));

                            Snackbar.make(parent, R.string.error_connection, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            ((BookRequestHolder) holder).rejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<SuccessResponse> call = ServerApiUtil.initUser().declineBooking(notif.getId().get$id(),
                            Person.getTokenMap());

                    call.enqueue(new Callback<SuccessResponse>() {
                        @Override
                        public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                            SuccessResponse success = response.body();

                            Log.d(TAG, "onResponse: resp = " + success);

                            if (success.isSuccess())
                                removeItem(notif, position);
                        }

                        @Override
                        public void onFailure(Throwable t) {

                            Log.e(TAG, "onFailure: error while accept request = " + Log.getStackTraceString(t));

                            Snackbar.make(parent, R.string.error_connection, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            ((BookRequestHolder) holder).details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            
        }

        if (holder instanceof BookFriendInviteHolder) {

            ((BookFriendInviteHolder) holder).itemBookFriendInviteBinding.setSpotName(notif.getSpot().getData().getName());
            ((BookFriendInviteHolder) holder).itemBookFriendInviteBinding.setUserName(notif.getUser().getData().getName());

            if (notif.getSpot().getData().getImgs().size() > 0)
                Picasso.with(context)
                        .load(Constant.URL_IMAGE + notif.getSpot().getData().getImgs().get(0))
                        .into(((BookFriendInviteHolder) holder).spotImg);

            if (notif.getUser().getData().getImgs().size() > 0)
                Picasso.with(context)
                        .load(Constant.URL_IMAGE + notif.getUser().getData().getImgs().get(0))
                        .resize(96, 96)
                        .transform(new CircleTransform(96))
                        .into(((BookFriendInviteHolder) holder).userPic);

            ((BookFriendInviteHolder) holder).yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<SuccessResponse> call = ServerApiUtil.initUser().confirmInvite(requestId, Person.getTokenMap());

                    call.enqueue(new Callback<SuccessResponse>() {
                        @Override
                        public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                            SuccessResponse successResponse = response.body();

                            if (successResponse == null) {

                                Toast.makeText(context, R.string.error_connection, Toast.LENGTH_SHORT).show();
                            } else {

                                if (successResponse.isSuccess()) {

                                    Toast.makeText(context, R.string.successfull_invite, Toast.LENGTH_SHORT).show();

                                    removeItem(notif, position);
                                } else {

                                    Toast.makeText(context, R.string.credit_card_issue, Toast.LENGTH_SHORT).show();
                                    
                                    removeItem(notif, position);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                            Toast.makeText(context, R.string.error_connection, Toast.LENGTH_SHORT).show();

                            Log.e(TAG, "onFailure: error while accept invite = " + Log.getStackTraceString(t));
                        }
                    });
                }
            });
            
            ((BookFriendInviteHolder) holder).noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<SuccessResponse> call = ServerApiUtil.initUser().declineInvite(requestId, Person.getTokenMap());

                    call.enqueue(new Callback<SuccessResponse>() {
                        @Override
                        public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                            SuccessResponse successResponse = response.body();

                            if (successResponse == null) {

                                Toast.makeText(context, R.string.error_connection, Toast.LENGTH_SHORT).show();
                            } else {

                                if (successResponse.isSuccess()) {

                                    Toast.makeText(context, R.string.successfull_decline, Toast.LENGTH_SHORT).show();

                                    removeItem(notif, position);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                            Toast.makeText(context, R.string.error_connection, Toast.LENGTH_SHORT).show();

                            Log.e(TAG, "onFailure: error while decline invite = " + Log.getStackTraceString(t));
                        }
                    });
                }
            });

            ((BookFriendInviteHolder) holder).readMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private void removeItem(Message notif, int position) {

        this.notifsList.remove(notif);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }

    @Override
    public int getItemCount() {
        return notifsList.size();
    }

    @Override
    public int getItemViewType(int position) {

        Message message = getItem(position);

        switch (message.getRequestType()) {

            case "book_verified":
                return BOOK_VERIFIED;

            case "book_request":
                return BOOK_REQUEST;

            case "book_pending_verify":
                return BOOK_PENDING_VERIFY;

            case "book_pending_invite":
                return BOOK_INVITED;
        }

        return -1;
    }

    private Message getItem(int position) {

        return this.notifsList.get(position);
    }

    public void addAll(List<Message> notifsArray) {

        this.notifsList.addAll(notifsArray);
        notifyDataSetChanged();
    }

    public void cleanArray() {

        this.notifsList.clear();
        notifyDataSetChanged();
    }

    public void setParent(RecyclerView parent) {
        this.parent = parent;
    }

    public class BookVirifiedHolder extends RecyclerView.ViewHolder {

        ItemBookVerifiedBinding binding;

        @BindView(R.id.spot_img)
        ImageView spotImg;

        @BindView(R.id.available)
        TextView available;

        @BindView(R.id.host_avatar)
        ImageView avatar;

        @BindView(R.id.remove)
        ImageView remove;

        public BookVirifiedHolder(ItemBookVerifiedBinding itemView) {
            super(itemView.getRoot());

            this.binding = itemView;

            ButterKnife.bind(this, binding.getRoot());
        }
    }

    public class BookRequestHolder extends RecyclerView.ViewHolder {

        ItemBookRequestBinding itemBookRequestBinding;

        @BindView(R.id.details_button)
        TextView details;

        @BindView(R.id.accept_button)
        TextView acceptButton;

        @BindView(R.id.reject_button)
        TextView rejectButton;

        @BindView(R.id.spot_img)
        ImageView spotImg;

        public BookRequestHolder(ItemBookRequestBinding itemBookRequestBinding) {
            super(itemBookRequestBinding.getRoot());

            this.itemBookRequestBinding = itemBookRequestBinding;

            ButterKnife.bind(this, itemBookRequestBinding.getRoot());
        }
    }

    private class StubHolder extends RecyclerView.ViewHolder {
        public StubHolder(View view) {
            super(view);
        }
    }

    public class BookFriendInviteHolder extends RecyclerView.ViewHolder {

        ItemBookFriendInviteBinding itemBookFriendInviteBinding;

        @BindView(R.id.yes_btn)
        TextView yesButton;

        @BindView(R.id.no_btn)
        TextView noButton;

        @BindView(R.id.spot_img)
        ImageView spotImg;

        @BindView(R.id.user_pic)
        ImageView userPic;

        @BindView(R.id.read_more)
        TextView readMore;

        public BookFriendInviteHolder(ItemBookFriendInviteBinding bookFriendInviteBinding) {
            super(bookFriendInviteBinding.getRoot());

            this.itemBookFriendInviteBinding = bookFriendInviteBinding;

            ButterKnife.bind(this, bookFriendInviteBinding.getRoot());
        }
    }
}
