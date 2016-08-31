package com.gridyn.potspot.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.Gson;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.query.LoginQuery;
import com.gridyn.potspot.response.UserInfoResponse;
import com.gridyn.potspot.response.UserLoginResponse;
import com.gridyn.potspot.service.GCMRegistrationIntentService;
import com.gridyn.potspot.service.UserService;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcast;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onGoogleMessageService();
        ifLoginTrue();
    }

    private void onGoogleMessageService() {
        mRegistrationBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               /* if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    String token = intent.getStringExtra("token");
                    Log.i(Constant.LOG, "GCM token:" + token);
                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Log.i(Constant.LOG, "GCM registration error!!!");
                }*/
                try {
                    Uri notification = RingtoneManager
                            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
                            notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(context, ChatActivity.class), PendingIntent.FLAG_ONE_SHOT);
                Notification.Builder builder = new Notification.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText(intent.getStringExtra("message"))
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(1010, builder.build());
            }
        };

        registerReceiver(mRegistrationBroadcast, new IntentFilter(Constant.MESSAGE_ACTION));

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (ConnectionResult.SUCCESS != resultCode) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Log.i(Constant.LOG, "Google Play Service is not install/enabled in this device!");
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
            } else {
                Log.i(Constant.LOG, "This device doesnt support for Google Play Service!");
            }
        } else {
            Intent intent = new Intent(this, GCMRegistrationIntentService.class);
            startService(intent);
        }
    }

    private void ifLoginTrue() {
        Person.getInstance();
        SharedPreferences settings = getSharedPreferences(Constant.APP_PREFERENCES, Context.MODE_PRIVATE);
        if (settings.getBoolean(Constant.AP_LOG_IN, false)) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
            final Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constant.BASE_URL)
                    .build();

            final LoginQuery query = new LoginQuery();
            if (settings.contains(Constant.AP_EMAIL)) {
                query.email = settings.getString(Constant.AP_EMAIL, "");
            }
            if (settings.contains(Constant.AP_PASSWORD)) {
                query.password = settings.getString(Constant.AP_PASSWORD, "");
            }
            query.androidId = Person.getAndroidId();

            Log.i(Constant.LOG, "ifLoginTrue: " + new Gson().toJson(query));

            final UserService service = retrofit.create(UserService.class);
            Call<UserLoginResponse> call = service.loginUser(query);

            call.enqueue(new Callback<UserLoginResponse>() {
                @Override
                public void onResponse(Response<UserLoginResponse> response, Retrofit retrofit) {
                    UserLoginResponse res = response.body();

                    if (res.success) {
                        Log.i(Constant.LOG, "onResponse: true");
                        Person.setId(res.message.get(1).id);
                        Person.setToken(res.message.get(0).token);
                        Log.i(Constant.LOG, "Token: " + res.message.get(0).token + "\nid: " + res.message.get(1).id);
                        getUserInfo(service);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR + ": user/login", Snackbar.LENGTH_SHORT).show();
                }
            });
        } else {
            setContentView(R.layout.activity_main);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            setFonts();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcast);
    }

    private void setFonts() {
        final AssetManager asset = getAssets();
        final TextView textViewPotSpot = (TextView) findViewById(R.id.tv_potspot);
        final TextView textViewUnderPotSpot = (TextView) findViewById(R.id.tv_under_potspot);
        final Button google = (Button) findViewById(R.id.btn_google);
        final Button facebook = (Button) findViewById(R.id.btn_facebook);
        final Button logIn = (Button) findViewById(R.id.first_log_in);
        final Button signUp = (Button) findViewById(R.id.first_sign_up);
        assert textViewPotSpot != null;
        assert textViewUnderPotSpot != null;
        assert google != null;
        assert facebook != null;
        assert logIn != null;
        assert signUp != null;
        textViewPotSpot.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        textViewUnderPotSpot.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Light.ttf"));
//        google.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
//        facebook.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        logIn.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        signUp.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
    }

    public void onCLickLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("isReg", false);
        startActivity(intent);
        finish();
    }

    public void onCLickSignUpActivity(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void getUserInfo(UserService service) {
        Map<String, String> mapToken = new HashMap<>();
        mapToken.put("token", Person.getToken());
        Call<UserInfoResponse> call = service.getUserInfo(Person.getId(), mapToken);

        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(final retrofit.Response<UserInfoResponse> response, Retrofit retrofit) {
                final Intent intent = new Intent(MainActivity.this, TabsActivity.class);
                Log.i("profile", String.valueOf(response.code()));

                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                UserInfoResponse res = response.body();
                UserInfoResponse.Message message = res.message.get(0);
                Person.setHost(message.system.isVerified);
                intent.putExtra("name", message.data.name);
                intent.putExtra("email", message.data.email);
                Log.i(Constant.LOG, "android id from the server: " + message.system.androidId);
                try {
                    intent.putExtra("avatar", message.data.imgs.get(0));
                } catch (IndexOutOfBoundsException e) {
                    intent.putExtra("avatar", Constant.BASE_IMAGE);
                }

                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR + ": user/{id}", Snackbar.LENGTH_SHORT).show();
                Log.i(Constant.LOG, "user/{id}" + t.getMessage());
            }
        });
    }
}
