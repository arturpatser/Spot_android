package com.gridyn.potspot.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;

public class GCMRegistrationIntentService extends IntentService {

    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";

    public GCMRegistrationIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        registerGCM();
    }

    private void registerGCM() {
        Intent registrationComplete;
        String token;
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i(Constant.LOG, "token:" + token);
            registrationComplete = new Intent(REGISTRATION_SUCCESS);
            registrationComplete.putExtra("token", token);
            Person.setAndroidId(token);
        } catch (Exception e) {
            registrationComplete = new Intent(REGISTRATION_ERROR);
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}
