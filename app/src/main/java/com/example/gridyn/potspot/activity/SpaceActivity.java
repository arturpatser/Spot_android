package com.example.gridyn.potspot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.example.gridyn.potspot.FastBlur;
import com.example.gridyn.potspot.R;

public class SpaceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);
        initToolbar();
        initHeader();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.space_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initHeader() {
        final FrameLayout header = (FrameLayout) findViewById(R.id.space_header);
        FastBlur.setBackgroundBlur(header, getApplicationContext(),
                "images/chairs.jpg", getResources());
    }
}
