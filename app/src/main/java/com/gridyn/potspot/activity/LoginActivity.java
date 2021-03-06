package com.gridyn.potspot.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.query.LoginQuery;
import com.gridyn.potspot.response.UserInfoResponse;
import com.gridyn.potspot.response.UserLoginResponse;
import com.gridyn.potspot.service.UserService;
import com.gridyn.potspot.utils.ServerApiUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private UserService mService;
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        ifReg();
        initFields();
        initToolbar();
        setFonts();
        initRetrofit();
    }

    private void ifReg() {
        if (getIntent().getExtras().getBoolean("isReg")) {
            Snackbar.make(findViewById(android.R.id.content), "Registration successful", Snackbar.LENGTH_LONG).show();
        }
    }

    private void initFields() {
        mEmail = (EditText) findViewById(R.id.log_in_email);
        mPassword = (EditText) findViewById(R.id.log_in_password);
        mSettings = getSharedPreferences(Constant.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = ServerApiUtil.initUser();
    }

    private void setFonts() {
        AssetManager asset = getAssets();
        final TextView textViewLogIn = (TextView) findViewById(R.id.tv_log_in);
        final TextView textViewUnderLogIn = (TextView) findViewById(R.id.tv_under_log_in);
        final Button logIn = (Button) findViewById(R.id.second_log_in);
        final TextView textForgotLogin = (TextView) findViewById(R.id.tv_btn_forgot_login);
        assert textViewLogIn != null;
        assert textViewUnderLogIn != null;
        assert logIn != null;
        assert textForgotLogin != null;
        textViewLogIn.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        textViewUnderLogIn.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Light.ttf"));
        logIn.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        textForgotLogin.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.log_in_toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            });
        }
    }

    public void onClickLogIn(final View view) {
        final LoginQuery query = new LoginQuery();
        query.email = mEmail.getText().toString().trim();
        query.password = mPassword.getText().toString().trim();
        query.androidId = Person.getAndroidId();

        Log.i(Constant.LOG, "onClickLogIn: " + new Gson().toJson(query));

        Call<UserLoginResponse> call = mService.loginUser(query);

        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Response<UserLoginResponse> response, Retrofit retrofit) {
                UserLoginResponse res = response.body();

                if (res.success) {
                    Log.i(Constant.LOG, "onResponse: true");
                    Person.setId(res.message.get(1).id);
                    Person.setToken(res.message.get(0).token);
                    final SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(Constant.AP_EMAIL, mEmail.getText().toString().trim());
                    editor.putString(Constant.AP_PASSWORD, mPassword.getText().toString().trim());
                    editor.putBoolean(Constant.AP_LOG_IN, true);
                    editor.apply();
                    Log.i(Constant.LOG, "Token: " + res.message.get(0).token + "\nid: " + res.message.get(1).id);
                    getUserInfo();
                } else {
                    Snackbar.make(view, "Incorrect email or password", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i(Constant.LOG, t.toString());
                Snackbar.make(view, Constant.CONNECTION_ERROR + ": user/login", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickForgotLogin(View view) {
        Intent intent = new Intent(this, ForgotActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void getUserInfo() {
        Map<String, String> mapToken = new HashMap<>();
        mapToken.put("token", Person.getToken());
        Call<UserInfoResponse> call = mService.getUserInfo(Person.getId(), mapToken);

        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(retrofit.Response<UserInfoResponse> response, Retrofit retrofit) {
                final Intent intent = new Intent(LoginActivity.this, TabsActivity.class);
                Log.i("profile", String.valueOf(response.code()));
                UserInfoResponse res = response.body();
                UserInfoResponse.Message message = res.message.get(0);
                Person.setHost(message.system.isVerified);
                intent.putExtra("name", message.data.name);
                intent.putExtra("email", message.data.email);

                Person.setName(message.data.name);
                Person.setEmail(message.data.email);
                try {
                    intent.putExtra("avatar", message.data.imgs.get(0));
                    Person.setAvatar(message.data.imgs.get(0));
                } catch (IndexOutOfBoundsException e) {
                    intent.putExtra("avatar", Constant.BASE_IMAGE);
                    Person.setAvatar(Constant.BASE_IMAGE);
                }
                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR + ": user/{id}", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSettings.contains(Constant.AP_EMAIL)) {
            mEmail.setText(mSettings.getString(Constant.AP_EMAIL, ""));
        }
    }
}