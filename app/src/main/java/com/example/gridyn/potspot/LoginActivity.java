package com.example.gridyn.potspot;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initToolbar();
        setFonts();
        Person.setHost();
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

    public void onClickLogIn(View view) {
        Intent intent = new Intent(this, TabsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
}