package com.gridyn.potspot.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.gridyn.potspot.activity.ClientPopup;
import com.gridyn.potspot.activity.HostNewRequestPopup;
import com.gridyn.potspot.activity.InvitedToJoinPopup;
import com.gridyn.potspot.activity.MainActivity;
import com.gridyn.potspot.model.AcceptRequestModel;
import com.gridyn.potspot.model.BookRequestData;
import com.gridyn.potspot.model.BookRequestModel;
import com.gridyn.potspot.model.FriendInviteResultModel;
import com.gridyn.potspot.model.bookInvite.BookInviteModel;
import com.gridyn.potspot.model.bookInvite.Data;
import com.gridyn.potspot.model.events.ReceivedNotifEvent;

import org.greenrobot.eventbus.EventBus;

import static com.gridyn.potspot.Constant.LOG;
import static com.gridyn.potspot.Constant.MESSAGE_ACTION;

public class GCMPushReceiverService extends GcmListenerService {

    private static final String TAG = GCMPushReceiverService.class.getName();
    Gson gson = new Gson();

    @Override
    public void onMessageReceived(String s, Bundle bundle) {

        String type = bundle.getString("message");
        String data = bundle.getString("data");
        String notificationMessage = "";

        Log.i(LOG, "message type: " + type);
        Log.d(TAG, "onMessageReceived: data = " + data);
        Log.d(TAG, "onMessageReceived: s = " + s);

        switch (type) {

            case "accept_request":
                AcceptRequestModel acceptReqModel = gson.fromJson(data, AcceptRequestModel.class);

                Log.d(TAG, "onMessageReceived: acceptReqModel = " + acceptReqModel);

                EventBus.getDefault().postSticky(new ReceivedNotifEvent());

                if (acceptReqModel.getAcceptRequestData().isSuccess()) {

                    notificationMessage = getString(R.string.book_request_accepted);

                    Intent popupIntent = new Intent(this, ClientPopup.class);
                    popupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    popupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    popupIntent.putExtra(Constant.SPOT_NAME, acceptReqModel.getAcceptRequestData().getSpotName());
                    popupIntent.putExtra(Constant.NOTIF_TEXT, notificationMessage);
                    popupIntent.putExtra(Constant.SPOT_ID, acceptReqModel.getAcceptRequestData().getSpotId());
                    popupIntent.putExtra(Constant.REQUEST_ID, acceptReqModel.getAcceptRequestData().getRequestId());
                    popupIntent.putExtra(Constant.PARTY_SIZE, acceptReqModel.getAcceptRequestData().getPartySize());
                    popupIntent.putExtra(Constant.SPOT_PRICE, acceptReqModel.getAcceptRequestData().getSpotPrice());

                    startActivity(popupIntent);
                }
            break;

            case "book_request":

                BookRequestModel bookRequestModel = gson.fromJson(data, BookRequestModel.class);

                BookRequestData bookData = bookRequestModel.getBookRequestData();

                Log.d(TAG, "onMessageReceived: book " + bookRequestModel);

                notificationMessage = getString(R.string.have_new_book);

                Intent popupIntent = new Intent(this, HostNewRequestPopup.class);
                popupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                popupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                popupIntent.putExtra(Constant.DATE_TIME_REQUEST, bookData.getDate());
                popupIntent.putExtra(Constant.SPOT_NAME, bookData.getSpotName());
                popupIntent.putExtra(Constant.SPOT_ID, bookData.getSpotId());
                popupIntent.putExtra(Constant.REQUEST_ID, bookData.getRequestId());

                EventBus.getDefault().postSticky(new ReceivedNotifEvent());

                startActivity(popupIntent);
                break;

            case "message_new":

                break;

            case "book_friend_invite":

                BookInviteModel bookInviteModel = gson.fromJson(data, BookInviteModel.class);

                Data inviteData = bookInviteModel.getData();

                notificationMessage = getString(R.string.join_spot);

                Intent popupIntentInvite = new Intent(this, InvitedToJoinPopup.class);

                popupIntentInvite.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                popupIntentInvite.putExtra(Constant.ARG_SPOT_ID, inviteData.getSpotId());
                popupIntentInvite.putExtra(Constant.ARG_WHO_INVITE, inviteData.getUserName());
                popupIntentInvite.putExtra(Constant.ARG_WHO_INVITE_ID, inviteData.getUserId());
                popupIntentInvite.putExtra(Constant.SPOT_NAME, inviteData.getSpotName());
                popupIntentInvite.putExtra(Constant.REQUEST_ID, inviteData.getRequestId());
                popupIntentInvite.putExtra(Constant.SPOT_PRICE, inviteData.getSpotPrice());

                startActivity(popupIntentInvite);

                Log.d(TAG, "onMessageReceived: received request invite = " + inviteData);

                break;

            case "book_friend_remove":

                break;

            case "book_friend_accept":

                FriendInviteResultModel friendInviteResultModel = gson.fromJson(data, FriendInviteResultModel.class);

                Intent intent = new Intent(Constant.INTENT_INVITE_FRIEND);
                // You can also include some extra data.
                intent.putExtra(Constant.SUCCESS, friendInviteResultModel.getData().isSuccess());
                intent.putExtra(Constant.USER_ID, friendInviteResultModel.getData().getUserId());
                intent.putExtra(Constant.USER_NAME, friendInviteResultModel.getData().getUserName());
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                break;

            case "friend_invite":

                break;

            case "friend_accept":

                break;

            case "spot_comment":

                break;

            case "user_comment":

                break;
        }

        if (!notificationMessage.equals("")) {

            Intent intent = new Intent(MESSAGE_ACTION);
            intent.putExtra("message", bundle.getString("message"));
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(intent);

            sendNotification(notificationMessage);
        }
    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(message)
                .setContentText(message)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
