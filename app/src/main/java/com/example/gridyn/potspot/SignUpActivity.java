package com.example.gridyn.potspot;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setFonts();
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

    public void onClickSignUp(View view) {
        Snackbar.make(view, "Sign Up", Snackbar.LENGTH_SHORT).show();
    }

    public void onCliCkBack(View view) {
        finish();
    }
}
