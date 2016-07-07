package com.example.gridyn.potspot.activity;

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

import com.example.gridyn.potspot.Constant;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.response.UserCreateResponse;
import com.example.gridyn.potspot.service.UserService;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private UserService mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initToolbar();
        setFonts();
        initRetrofit();
        createUser();
    }

    private void initRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = retrofit.create(UserService.class);
    }

    private void createUser() {
        mName = (EditText) findViewById(R.id.sign_up_name);
        mEmail = (EditText) findViewById(R.id.sign_up_email);
        mPassword = (EditText) findViewById(R.id.sign_up_password);


    }

    private void setFonts() {
        AssetManager asset = getAssets();
        final TextView textViewSignUp = (TextView) findViewById(R.id.tv_sign_up);
        final TextView textViewUnderSignUp = (TextView) findViewById(R.id.tv_under_sign_up);
        final Button signUp = (Button) findViewById(R.id.second_sign_up);
        assert textViewSignUp != null;
        assert textViewUnderSignUp != null;
        assert signUp != null;
        textViewSignUp.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        textViewUnderSignUp.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Light.ttf"));
        signUp.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
    }

    public void onClickSignUp(final View view) {
        final String name = mName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();

        Map<String, String> mapJson = new HashMap<>();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Snackbar.make(view, "Input all fields", Snackbar.LENGTH_SHORT).show();
        } else {
            mapJson.put("name", mName.getText().toString());
            mapJson.put("email", mEmail.getText().toString());
            mapJson.put("password", mPassword.getText().toString());

            Call<UserCreateResponse> call = mService.createUser(mapJson);

            call.enqueue(new Callback<UserCreateResponse>() {
                @Override
                public void onResponse(Response<UserCreateResponse> response, Retrofit retrofit) {
                    UserCreateResponse res = response.body();
                    Log.i("signup", "Response status code: " + response.code());

                    if (res.success) {
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Snackbar.make(view, "Input data is not correct.", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Snackbar.make(view, Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.sign_up_toolbar);
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
}
