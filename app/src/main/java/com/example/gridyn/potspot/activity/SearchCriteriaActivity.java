package com.example.gridyn.potspot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.gridyn.potspot.R;

public class SearchCriteriaActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar mSeekRadius;
    private TextView mRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_criteria);

        mSeekRadius = (SeekBar) findViewById(R.id.sch_seek_bar);
        mSeekRadius.setOnSeekBarChangeListener(this);

        mRadius = (TextView) findViewById(R.id.sch_radius);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mRadius.setText(String.valueOf(mSeekRadius.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
