package com.example.gridyn.potspot;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setFonts();
        Person.getInstance();
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
        google.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        facebook.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        logIn.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        signUp.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
    }

    public void onCLickLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onCLickSignUpActivity(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
