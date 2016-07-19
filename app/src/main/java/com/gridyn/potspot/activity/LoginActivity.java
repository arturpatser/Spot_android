package com.gridyn.potspot.activity;

import android.content.Intent;
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

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.response.UserInfoResponse;
import com.gridyn.potspot.response.UserLoginResponse;
import com.gridyn.potspot.service.UserService;

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
    private boolean isReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        ifReg();
        initToolbar();
        setFonts();
        initRetrofit();
        initFields();
    }

    private void ifReg() {
        isReg = getIntent().getExtras().getBoolean("isReg");
        if (isReg) {
            Snackbar.make(findViewById(android.R.id.content), "Account recorded", Snackbar.LENGTH_LONG).show();
        }
    }

    private void initFields() {
        mEmail = (EditText) findViewById(R.id.log_in_email);
        mPassword = (EditText) findViewById(R.id.log_in_password);
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = retrofit.create(UserService.class);
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
                    finish();
                }
            });
        }
    }

    public void onClickLogIn(final View view) {
        final Map<String, String> map = new HashMap<>();
        map.put("email", mEmail.getText().toString().trim());
        map.put("password", mPassword.getText().toString().trim());
//        map.put("email", "rpugase@gmail.com");
//        map.put("password", "qwerty123");

        Call<UserLoginResponse> call = mService.loginUser(map);

        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Response<UserLoginResponse> response, Retrofit retrofit) {
                UserLoginResponse res = response.body();

                if (res.success) {
                    Log.i(Constant.LOG, "onResponse: true");
                    Person.setToken(res.message.get(0).token);
                    Person.setId(res.message.get(1).id);
                    getUserInfo();
                } else {
                    Snackbar.make(view, "Incorrect email or password", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(view, Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickForgotLogin(View view) {
        Intent intent = new Intent(this, ForgotActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void getUserInfo() {
        Call<UserInfoResponse> call = mService.getUserInfo(Person.getId());

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
                try {
                    intent.putExtra("avatar", message.data.imgs[0]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    intent.putExtra("avatar", Constant.BASE_IMAGE);
                }
                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}