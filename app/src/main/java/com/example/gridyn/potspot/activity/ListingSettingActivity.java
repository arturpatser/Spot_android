package com.example.gridyn.potspot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gridyn.potspot.R;

import java.util.Map;

public class ListingSettingActivity extends AppCompatActivity {

    private Map<Integer, Boolean> flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_setting);
        initToolbar();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.list_set_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onClickDayOfWeek(View view) {
        switch (view.getId()) {
            case R.id.btn_sunday:

                break;
            case R.id.btn_monday:

                break;
            case R.id.btn_tuesday:

                break;
            case R.id.btn_wednesday:

                break;
            case R.id.btn_thursday:

                break;
            case R.id.btn_friday:

                break;
            case R.id.btn_saturday:

                break;
        }
    }
}
