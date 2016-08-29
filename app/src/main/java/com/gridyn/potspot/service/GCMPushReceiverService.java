package com.gridyn.potspot.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.gridyn.potspot.activity.ClientPopup;
import com.gridyn.potspot.activity.MainActivity;
import com.gridyn.potspot.model.AcceptRequestModel;

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

                if (acceptReqModel.getAcceptRequestData().isSuccess()) {

                    notificationMessage = getString(R.string.book_request_accepted);

                    acceptReqModel.getAcceptRequestData().setSpotName("askljfaklsjfa");

                    Intent popupIntent = new Intent(this, ClientPopup.class);
                    popupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    popupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    popupIntent.putExtra(Constant.SPOT_NAME, acceptReqModel.getAcceptRequestData().getSpotName());
                    popupIntent.putExtra(Constant.NOTIF_TEXT, notificationMessage);
                    popupIntent.putExtra(Constant.SPOT_ID, acceptReqModel.getAcceptRequestData().getSpotId());

                    startActivity(popupIntent);
                }
            break;

            case "message_new":

                break;

            case "book_friend_invite":

                break;

            case "book_friend_remove":

                break;

            case "book_friend_accept":

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
