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
import com.gridyn.potspot.activity.MainActivity;

import static com.gridyn.potspot.Constant.LOG;
import static com.gridyn.potspot.Constant.MESSAGE_ACTION;

public class GCMPushReceiverService extends GcmListenerService {
    @Override
    public void onMessageReceived(String s, Bundle bundle) {
        sendNotification(bundle.getString("message"));
        Log.i(LOG, "GCM message: " + bundle.getString("message"));
        Intent intent = new Intent(MESSAGE_ACTION);
        intent.putExtra("message", bundle.getString("message"));
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentText(message)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
