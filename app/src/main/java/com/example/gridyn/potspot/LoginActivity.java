package com.example.gridyn.potspot;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

        setFonts();
    }

    public void onClickLogIn(View view) {
        Snackbar.make(view, "Log In", Snackbar.LENGTH_SHORT).show();
    }

    private void setFonts() {
        AssetManager asset = getAssets();
        final TextView textViewLogIn = (TextView) findViewById(R.id.tv_log_in);
        final TextView textViewUnderLogIn = (TextView) findViewById(R.id.tv_under_log_in);
        final Button logIn = (Button) findViewById(R.id.second_log_in);
        assert textViewLogIn != null;
        assert textViewUnderLogIn != null;
        assert logIn != null;
        textViewLogIn.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        textViewUnderLogIn.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Light.ttf"));
        logIn.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
    }

    public void onCliCkBack(View view) {
        finish();
    }
}
