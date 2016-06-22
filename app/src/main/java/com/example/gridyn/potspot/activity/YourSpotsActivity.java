package com.example.gridyn.potspot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.gridyn.potspot.AssetsHelper;
import com.example.gridyn.potspot.R;

public class YourSpotsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_spots);
        initToolbar();
        setBackground();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.your_spot_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setBackground() {
        final LinearLayout background = (LinearLayout) findViewById(R.id.your_spot_back);
        background.setBackground(AssetsHelper.loadImageFromAsset(getApplicationContext(), "images/chairs.jpg"));
    }
}
