package com.example.gridyn.potspot.activity;

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
    private TextView mRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_criteria);
        initToolbar();
        initPriceRange();
        initNumberOfGuests();

        mSeekRadius = (TextThumbSeekBar) findViewById(R.id.sch_seek_bar);
        mSeekRadius.setOnSeekBarChangeListener(this);

        mRadius = (TextView) findViewById(R.id.sch_radius);
    }

    private void initNumberOfGuests() {
        final Button minus = (Button) findViewById(R.id.sch_minus);
        final Button plus = (Button) findViewById(R.id.sch_plus);
        final TextView countGuests = (TextView) findViewById(R.id.sch_count_guests);
        assert minus != null;
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(countGuests.getText().toString());
                if(i > 0) {
                    i--;
                }
                countGuests.setText(String.valueOf(i));
            }
        });

        assert plus != null;
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(countGuests.getText().toString());
                if(i >= 0) {
                    i++;
                }
                countGuests.setText(String.valueOf(i));
            }
        });
    }

    private void initPriceRange() {
        final RangeBar priceRange = (RangeBar) findViewById(R.id.sch_seek_price);
        final TextView interval = (TextView) findViewById(R.id.sch_interval);
        assert priceRange != null;
        priceRange.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int left, int right) {
                interval.setText("$" + left + " - $" + right);
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

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
