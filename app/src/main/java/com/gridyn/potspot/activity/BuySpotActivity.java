package com.gridyn.potspot.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.response.SpotInfoResponse;
import com.gridyn.potspot.service.ServerApiUtil;
import com.gridyn.potspot.service.SpotService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class BuySpotActivity extends AppCompatActivity {

    private Context mContext;
    private ImageView mHeader;
    private TextView mName;
    private TextView mUnderName;
    private TextView mPartySize;
    private TextView mTotalPrice;
    private TextView mDate;
    private TextView mTime;
    private Button mPay;
    private Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_spot);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        initFields();
        initRetrofit();
        initToolbar();
    }

    private void initFields() {
        mContext = getApplicationContext();
        mHeader = (ImageView) findViewById(R.id.buy_header);
        mName = (TextView) findViewById(R.id.buy_name);
        mUnderName = (TextView) findViewById(R.id.buy_under_name);
        mPartySize = (TextView) findViewById(R.id.buy_party_size);
        mTotalPrice = (TextView) findViewById(R.id.buy_total_price);
        mDate = (TextView) findViewById(R.id.buy_date);
        mTime = (TextView) findViewById(R.id.buy_time);
        mPay = (Button) findViewById(R.id.buy_pay);
        mCalendar = Calendar.getInstance();
        mDate.setText(Integer.toString(mCalendar.get(Calendar.DAY_OF_MONTH)) + "/"
                + mCalendar.get(Calendar.MONTH) + "/" + mCalendar.get(Calendar.YEAR));
    }

    private void initRetrofit() {

        final SpotService service = ServerApiUtil.initSpot();

        Call<SpotInfoResponse> call = service.getSpot(getIntent().getExtras().getString("id"), Person.getTokenMap());
        call.enqueue(new Callback<SpotInfoResponse>() {
            @Override
            public void onResponse(Response<SpotInfoResponse> response, Retrofit retrofit) {
                if (response.body().success) {
                    Log.i(Constant.LOG, new Gson().toJson(response.body()));

                    //TODO check class parse
//                    SpotInfoResponse.Message.Spot.Data spot = response.body().message.get(0).spots.get(0).data;
//
//                    Picasso.with(mContext)
//                            .load(Constant.URL_IMAGE + spot.imgs.get(0))
//                            .into(mHeader);
//
//                    mName.setText(spot.name);
//                    mUnderName.setText(spot.type + " | " + "спроси у Ильи про дату");
//                    mPartySize.setText(spot.maxGuests + "\nparty size");
//                    mTotalPrice.setText("$" + spot.price + "\ntotal price");
//                    mPay.setText("pay $" + spot.price);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.buy_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onClickVisitDate(View view) {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                if (dayOfMonth >= mCalendar.get(Calendar.DAY_OF_MONTH) && monthOfYear >= mCalendar.get(Calendar.MONTH)
                        && year >= mCalendar.get(Calendar.YEAR))
                    mDate.setText(Integer.toString(dayOfMonth) + "/" + Integer.toString(monthOfYear)
                        + "/" + Integer.toString(year));
                else
                    Snackbar.make(findViewById(android.R.id.content), R.string.prev_date, Snackbar.LENGTH_SHORT).show();
            }
        };
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(callback,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setAccentColor(getResources().getColor(R.color.mainRed));
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    public void onClickVisitTime(View view) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                mTime.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
        };
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                callback, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
        );
        timePickerDialog.setAccentColor(getResources().getColor(R.color.mainRed));
        timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
    }
}
