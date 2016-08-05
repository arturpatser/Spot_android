package com.gridyn.potspot.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
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
        ifLoginTrue();
        mRegistrationBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    String token = intent.getStringExtra("token");
                    Log.i(Constant.LOG, "GCM token:" + token);
                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Log.i(Constant.LOG, "GCM registration error!!!");
                }
            }
        };

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

            final Map<String, String> map = new HashMap<>();
            if (settings.contains(Constant.AP_EMAIL)) {
                map.put("email", settings.getString(Constant.AP_EMAIL, ""));
            }
            if (settings.contains(Constant.AP_PASSWORD)) {
                map.put("password", settings.getString(Constant.AP_PASSWORD, ""));
            }

            final UserService service = retrofit.create(UserService.class);
            Call<UserLoginResponse> call = service.loginUser(map);

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
                    Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
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
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcast,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcast,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcast);
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
            public void onResponse(retrofit.Response<UserInfoResponse> response, Retrofit retrofit) {
                final Intent intent = new Intent(MainActivity.this, TabsActivity.class);
                Log.i("profile", String.valueOf(response.code()));
                UserInfoResponse res = response.body();
                UserInfoResponse.Message message = res.message.get(0);
                Person.setHost(message.system.isVerified);
                intent.putExtra("name", message.data.name);
                intent.putExtra("email", message.data.email);
                try {
                    intent.putExtra("avatar", message.data.imgs[0]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    intent.putExtra("avatar", Constant.BASE_IMAGE);
                }
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
