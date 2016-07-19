package com.gridyn.potspot.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edmodo.rangebar.RangeBar;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.gridyn.potspot.SearchBridge;
import com.gridyn.potspot.TextThumbSeekBar;
import com.gridyn.potspot.query.SearchCriteriaQuery;
import com.gridyn.potspot.response.SpotSearchResponse;
import com.gridyn.potspot.service.SpotService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SearchCriteriaActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView mCountGuests;
    private CheckBox mPatio;
    private CheckBox mBackyard;
    private CheckBox mSmokingRooms;
    private CheckBox mBalcony;
    private CheckBox mHeated;
    private CheckBox mHandicap;
    private CheckBox mTobacco;
    private int mStartPriceRange;
    private int mEndPriceRange;
    private int mRadiusInterval;
    private SpotService mService;
    private double mLat;
    private double mLng;

    private LocationManager mLocationManager;
    private Location mLocation;
    private MyLocationListener mLocationListener;
    private GoogleMap mMap;
    private Circle mCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_criteria);
        initFields();
        initLocation();
        initToolbar();
        initRetrofit();
        initPriceRange();
        initNumberOfGuests();
        initSeekRadius();
        initMap();
    }

    private void initMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_search);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLat, mLng), 11));
        drawCircle();
    }

    private void drawCircle() {
        if (mCircle != null) {
            mCircle.remove();
        }
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(mLat, mLng))
                .radius(mRadiusInterval * 750)
                .fillColor(Color.argb(30, 53, 175, 180))
                .strokeColor(Color.rgb(53, 175, 180))
                .strokeWidth(3);
        mCircle = mMap.addCircle(circleOptions);
    }

    private void initFields() {
        mCountGuests = (TextView) findViewById(R.id.sch_count_guests);
        mPatio = (CheckBox) findViewById(R.id.sch_patio);
        mBackyard = (CheckBox) findViewById(R.id.sch_backyard);
        mSmokingRooms = (CheckBox) findViewById(R.id.sch_smoking_rooms);
        mBalcony = (CheckBox) findViewById(R.id.sch_balcony);
        mHeated = (CheckBox) findViewById(R.id.sch_heated);
        mHandicap = (CheckBox) findViewById(R.id.sch_handicap);
        mTobacco = (CheckBox) findViewById(R.id.sch_tobacco);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    private void initLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        String provider = mLocationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mLocation = mLocationManager.getLastKnownLocation(provider);
        mLocationListener = new MyLocationListener();

        showCurrentLocation(mLocation);

        // Регистрируемся для обновлений
        mLocationManager.requestLocationUpdates(provider,
                Constant.UPDATE_LOCATION_SECONDS, Constant.UPDATE_LOCATION_DISTANCE,
                mLocationListener);
    }

    private void initRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = retrofit.create(SpotService.class);
    }

    private void initSeekRadius() {
        final TextThumbSeekBar seekRadius = (TextThumbSeekBar) findViewById(R.id.sch_seek_bar);
        assert seekRadius != null;
        seekRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRadiusInterval = progress;
                drawCircle();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initNumberOfGuests() {
        final Button minus = (Button) findViewById(R.id.sch_minus);
        final Button plus = (Button) findViewById(R.id.sch_plus);
        assert minus != null;
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = Integer.parseInt(mCountGuests.getText().toString());
                if (i > 0) {
                    i--;
                }
                mCountGuests.setText(Integer.toString(i));
            }
        });

        assert plus != null;
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = Integer.parseInt(mCountGuests.getText().toString());
                if (i >= 0) {
                    i++;
                }
                mCountGuests.setText(Integer.toString(i));
            }
        });
    }

    private void initPriceRange() {
        final TextView interval = (TextView) findViewById(R.id.sch_interval);
        mStartPriceRange = 0;
        mEndPriceRange = 101;
        interval.setText("$" + mStartPriceRange + " - $" + (mEndPriceRange - 1));

        final RangeBar mPriceRange = (RangeBar) findViewById(R.id.sch_seek_price);
        mPriceRange.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int left, int right) {
                mStartPriceRange = left;
                mEndPriceRange = right;
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

    public void onClickCheckSpots(View view) {
        SearchCriteriaQuery.Distance distance = new SearchCriteriaQuery.Distance();

//        distance.lat = mLat;
//        distance.lng = mLng;
//        distance.radius = mRadiusInterval;
        distance.lat = 55.755786;
        distance.lng = 37.617633;
        distance.radius = 100000;
        SearchCriteriaQuery query = new SearchCriteriaQuery();
        query.badges = getBudges();
        query.maxGuest = getGuests(Integer.parseInt(mCountGuests.getText().toString()));
        query.price = getPriceInterval(mStartPriceRange, mEndPriceRange);
        query.type = getSpotTypes();
        query.distance = distance;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.i(Constant.LOG, gson.toJson(query));
        Call<SpotSearchResponse> call = mService.searchSpot(query);
        Log.i(Constant.LOG, "query side");
        call.enqueue(new Callback<SpotSearchResponse>() {
            @Override
            public void onResponse(Response<SpotSearchResponse> response, Retrofit retrofit) {
                Log.i(Constant.LOG, "response side");
                if (response.body().success) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Log.i(Constant.LOG, gson.toJson(response.body()));

                    SearchBridge.setDataForSearchResult(response.body().message.get(0).spots);
                    final Intent intent = new Intent(SearchCriteriaActivity.this, SearchResultActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i(Constant.LOG, t.toString());
            }
        });
    }

    private List<String> getSpotTypes() {
        List<String> types = new ArrayList<>();
        if (mPatio.isChecked()) {
            types.add("patio");
        }

        if (mBackyard.isChecked()) {
            types.add("backyard");
        }

        if (mSmokingRooms.isChecked()) {
            types.add("smokingRooms");
        }

        if (mBalcony.isChecked()) {
            types.add("balcony");
        }

        return types;
    }

    private List<String> getBudges() {
        List<String> budges = new ArrayList<>();

        if (mTobacco.isChecked()) {
            budges.add("tobaccoFriendly");
        }

        if (mHeated.isChecked()) {
            budges.add("heated");
        }

        if (mHandicap.isChecked()) {
            budges.add("handicap");
        }

        return budges;
    }

    private int[] getGuests(int countGuests) {
        int[] guests = new int[2];
        guests[0] = 1;
        guests[1] = countGuests;
        return guests;
    }

    private int[] getPriceInterval(int start, int end) {
        int[] priceInterval = new int[2];
        priceInterval[0] = start;
        priceInterval[1] = end;
        return priceInterval;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Настройка");
            builder.setMessage("Сейчас GPS отлючён.\n" + "Включить?");
            builder.setPositiveButton("Да",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });
            builder.create().show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mLocationManager.removeUpdates(mLocationListener);
    }

    protected void showCurrentLocation(Location location) {
        if (location != null) {
            mLat = location.getLatitude();
            mLng = location.getLongitude();
        }
    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            mLat = location.getLatitude();
            mLng = location.getLongitude();
            String message = "Новое местоположение Долгота: " +
                    mLng + " Широта: " + mLat;
            Toast.makeText(SearchCriteriaActivity.this, message, Toast.LENGTH_LONG)
                    .show();
            showCurrentLocation(mLocation);
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(SearchCriteriaActivity.this, "Статус провайдера изменился",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(SearchCriteriaActivity.this,
                    "Провайдер заблокирован пользователем. GPS выключен",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(SearchCriteriaActivity.this,
                    "Провайдер включен пользователем. GPS включён",
                    Toast.LENGTH_LONG).show();
        }
    }
}
