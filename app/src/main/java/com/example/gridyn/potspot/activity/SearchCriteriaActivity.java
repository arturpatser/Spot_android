package com.example.gridyn.potspot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.TextThumbSeekBar;

public class SearchCriteriaActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextThumbSeekBar mSeekRadius;
    private TextView mCountGuests;
    private RangeBar mPriceRange;
    private TextView mInterval;

    private int mDoubeInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_criteria);
        initFields();
        initToolbar();
        initPriceRange();
        initNumberOfGuests();
    }

    private void initFields() {
        mCountGuests = (TextView) findViewById(R.id.sch_count_guests);
        mPriceRange = (RangeBar) findViewById(R.id.sch_seek_price);
        mInterval = (TextView) findViewById(R.id.sch_interval);
        mSeekRadius = (TextThumbSeekBar) findViewById(R.id.sch_seek_bar);
        mSeekRadius.setOnSeekBarChangeListener(this);
    }

    private void initNumberOfGuests() {
        final Button minus = (Button) findViewById(R.id.sch_minus);
        final Button plus = (Button) findViewById(R.id.sch_plus);
        assert minus != null;
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(mCountGuests.getText().toString());
                if(i > 0) {
                    i--;
                }
                mCountGuests.setText(String.valueOf(i));
            }
        });

        assert plus != null;
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(mCountGuests.getText().toString());
                if(i >= 0) {
                    i++;
                }
                mCountGuests.setText(String.valueOf(i));
            }
        });
    }

    private void initPriceRange() {
        assert mPriceRange != null;
        mPriceRange.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int left, int right) {
                mInterval.setText("$" + left + " - $" + right);
            }
        });

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.sch_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mDoubeInterval = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void onClickCheckSpots(View view) {
        //TODO: retrofit
        final Intent intent = new Intent(this, SearchResultActivity.class);
        startActivity(intent);
    }
}
