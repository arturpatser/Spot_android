package com.example.gridyn.potspot;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ForgotActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initToolbar();
        setFonts();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.forgot_toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    NavUtils.navigateUpFromSameTask(ForgotActivity.this);
                    finish();
                }
            });
        }
    }

    public void onClickReset(View view) {
        Snackbar.make(view, "Reset", Snackbar.LENGTH_SHORT).show();
    }

    private void setFonts() {
        AssetManager asset = getAssets();
        final TextView textForgotLogin = (TextView) findViewById(R.id.tv_forgot_login);
        final TextView textUnderForgotLogin = (TextView) findViewById(R.id.tv_under_forgot_login);
        final Button buttonForgotLogin = (Button) findViewById(R.id.btn_forgot_login);
        assert textForgotLogin != null;
        assert textUnderForgotLogin != null;
        assert buttonForgotLogin != null;
        textForgotLogin.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        textUnderForgotLogin.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Light.ttf"));
        buttonForgotLogin.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
    }
}
