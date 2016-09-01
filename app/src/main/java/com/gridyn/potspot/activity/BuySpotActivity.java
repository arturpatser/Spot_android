package com.gridyn.potspot.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.gridyn.potspot.adapter.SplitFriendsAdapter;
import com.gridyn.potspot.databinding.ActivityBuySpotBinding;
import com.gridyn.potspot.fragment.SelectFriendsFragment;
import com.gridyn.potspot.interfaces.BuySpotInterface;
import com.gridyn.potspot.model.FriendModel;
import com.gridyn.potspot.query.BookQuery;
import com.gridyn.potspot.response.BookResponse;
import com.gridyn.potspot.response.PaymentResponse;
import com.gridyn.potspot.response.SpotInfoResponse;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.service.SpotService;
import com.gridyn.potspot.utils.FragmentUtils;
import com.gridyn.potspot.utils.ServerApiUtil;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class BuySpotActivity extends AppCompatActivity implements BuySpotInterface {

    private static final String TAG = BuySpotActivity.class.getName();
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
    SpotInfoResponse.Message.Spot spot;
    RecyclerView splitFriendsRecycler;
    private SplitFriendsAdapter adapter;
    ActivityBuySpotBinding binding;
    boolean forBook;
    String spotId;
    private String sTimeFrom, sTimeTo;
    private String requestId;
    private int minsFrom = 0;
    private int minsTo = 0;
    private String partySize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        forBook = getIntent().getExtras().getBoolean(Constant.OPEN_FOR_BOOK);

        spotId = getIntent().getExtras().getString("id");
        requestId = getIntent().getExtras().getString(Constant.REQUEST_ID);
        partySize = getIntent().getExtras().getString(Constant.PARTY_SIZE);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_buy_spot);
        binding.setShowSplit(true);
        binding.setForBook(forBook);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initFields();
        initRetrofit();
        initToolbar();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Constant.INTENT_INVITE_FRIEND));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle args = intent.getExtras();

            Log.d(TAG, "onReceive: received event ");

            if (args != null) {

                boolean success = args.getBoolean(Constant.SUCCESS);
                String userId = args.getString(Constant.USER_ID);

                Log.d(TAG, "onReceive: received args =  " + success + " user =" + userId);

                adapter.updateItem(userId, success);
            }
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
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

        splitFriendsRecycler = (RecyclerView) findViewById(R.id.split_friends_recycler);

        adapter = new SplitFriendsAdapter(this, this);

        splitFriendsRecycler.setLayoutManager(new LinearLayoutManager(this));
        splitFriendsRecycler.setAdapter(adapter);

        if (forBook) {

            mPay.setText(getString(R.string.request_booking));
        }
    }

    private void initRetrofit() {

        final SpotService service = ServerApiUtil.initSpot();

        Call<SpotInfoResponse> call = service.getSpot(spotId, Person.getTokenMap());
        call.enqueue(new Callback<SpotInfoResponse>() {
            @Override
            public void onResponse(Response<SpotInfoResponse> response, Retrofit retrofit) {
                if (response.body().success) {
                    Log.i(Constant.LOG, new Gson().toJson(response.body()));

                    //TODO check class parse
                    spot = response.body().message.get(0).spots.get(1);

                    if (partySize == null)
                        partySize = String.valueOf(spot.maxGuests);

                    spot.price = spot.price / 100;

                    Log.d(TAG, "onResponse: spot = " + spot);

                    if (spot.imgs.size() > 0)
                    Picasso.with(mContext)
                            .load(Constant.URL_IMAGE + spot.imgs.get(0))
                            .into(mHeader);

                    mName.setText(spot.name);
                    mUnderName.setText(spot.type + " | " + "спроси у Ильи про дату");
                    mPartySize.setText(partySize + "\nparty size");
                    mTotalPrice.setText("$" + spot.price + "\ntotal price");
                    if (!forBook) {
                        mPay.setText("pay $" + spot.price);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e(TAG, "onFailure: error while load spot = " + Log.getStackTraceString(t));

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

                minsFrom = hourOfDay * 60 + minute;

                sTimeFrom = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);

                TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

                        minsTo = hourOfDay * 60 + minute;

                        sTimeTo = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);

                        Log.d(TAG, "onTimeSet: timestay = " + (minsTo - minsFrom));

                        mTime.setText(sTimeFrom + " - " + sTimeTo);
                    }
                };
                final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                        callback, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true
                );
                timePickerDialog.setTitle(getString(R.string.time_to));
                timePickerDialog.setAccentColor(getResources().getColor(R.color.mainRed));
                timePickerDialog.show(getFragmentManager(), "TimePickerDialogTo");
            }
        };
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                callback, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true
        );
        timePickerDialog.setTitle(getString(R.string.time_from));
        timePickerDialog.setAccentColor(getResources().getColor(R.color.mainRed));
        timePickerDialog.show(getFragmentManager(), "TimePickerDialogFrom");
    }

    public void onClickBuyPay(View view) {

        if (forBook) {

            //book process here
            if (spot != null) {

                int timeStay = minsTo - minsFrom;

                if (mTime.getText().equals("Set") || timeStay <= 0) {

                    Snackbar.make(findViewById(android.R.id.content), R.string.select_time, Snackbar.LENGTH_SHORT).show();
                } else {
                    final Snackbar progress = Snackbar.make(findViewById(android.R.id.content),
                            R.string.request_process, Snackbar.LENGTH_INDEFINITE);
                    progress.show();

                    BookQuery bookQuery = new BookQuery();

                    Format formatter;
                    //  vvvvvvvvvv  Add your date object here
                    Date date = new Date();

                    formatter = new SimpleDateFormat("EEEE MMMM dd, yyyy");

                    bookQuery.setGuests(Integer.parseInt(partySize));
                    bookQuery.setTimeStay(timeStay);
                    bookQuery.setDate(formatter.format(date));
                    bookQuery.setToken(Person.getToken());

                    Call<BookResponse> bookResponseCall = ServerApiUtil.initUser().bookSpot(spotId,
                            bookQuery);

                    bookResponseCall.enqueue(new Callback<BookResponse>() {
                        @Override
                        public void onResponse(Response<BookResponse> response, Retrofit retrofit) {

                            progress.dismiss();

                            BookResponse bookResp = response.body();

                            Log.d(TAG, "onResponse: response = " + bookResp);

                            if (bookResp.isSuccess()) {

                                goToTabs(getString(R.string.successfull_request));
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                            progress.dismiss();

                            Log.e(TAG, "onFailure: error while book = " + Log.getStackTraceString(t));

                            Snackbar.make(findViewById(android.R.id.content), R.string.error_connection, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        } else {

            //buy process here

            if (paymentPartyReady()) {

                final Snackbar progress = Snackbar.make(findViewById(android.R.id.content),
                        R.string.payment_progress, Snackbar.LENGTH_INDEFINITE);
                progress.show();

                Call<PaymentResponse> call = ServerApiUtil.initUser().startPaying(requestId, Person.getTokenMap());

                call.enqueue(new Callback<PaymentResponse>() {
                    @Override
                    public void onResponse(Response<PaymentResponse> response, Retrofit retrofit) {

                        PaymentResponse payment = response.body();

                        Log.d(TAG, "onResponse: response = " + payment);

                        if (payment.isSuccess()) {

                            goToTabs(getString(R.string.successfull_payment));

                            progress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                        Log.e(TAG, "onFailure: payment error = " + Log.getStackTraceString(t));

                        progress.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), R.string.error_connection, Snackbar.LENGTH_INDEFINITE).show();
                    }
                });
            } else {

                Snackbar.make(findViewById(android.R.id.content), R.string.not_all_friends_accepted, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private boolean paymentPartyReady() {

        if (adapter.getItemCount() == 0)
            return true;

        for (FriendModel friendModel : adapter.getItems()) {

            if (!friendModel.isAcceptedInvite())
                return false;

        }

        return true;
    }

    private void goToTabs(String mes) {

        Intent intent = new Intent(BuySpotActivity.this, TabsActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("name", Person.getName());
        intent.putExtra("email", Person.getEmail());
        intent.putExtra(Constant.PROGRESS_MESSAGE, mes);

        try {
            intent.putExtra("avatar", Person.getAvatar());

        } catch (IndexOutOfBoundsException e) {
            intent.putExtra("avatar", Constant.BASE_IMAGE);
        }

        startActivity(intent);
    }

    public void onClickSplitPayment(View view) {

        if (spot != null && spot.maxGuests != 1) {
            showSplitFriends();
        } else
            Snackbar.make(findViewById(android.R.id.content), R.string.cannot_split, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSplitFriends() {

        SelectFriendsFragment select = SelectFriendsFragment.newInstance(Integer.parseInt(partySize) - 1,
                adapter.getItems(), requestId);

        select.setOnSelectFriendsListener(new SelectFriendsFragment.OnSelectFriendsListener() {
            @Override
            public void friendsSelected(ArrayList<FriendModel> selectedItems) {

                //TODO process selected friends here
                Log.d(TAG, "friendsSelected: process friends started " + selectedItems);
                adapter.clear();
                if (selectedItems.size() > 0) {

                    for (final FriendModel f :
                            selectedItems) {

                        float fSplitPrice = (float) spot.price / (selectedItems.size() + 1);

                        //TODO change price
                        f.setSplitSize(fSplitPrice);

                        Call<SuccessResponse> call = ServerApiUtil.initUser()
                                .addFriendToBooking(requestId, f.getId(), Person.getTokenMap());

                        call.enqueue(new Callback<SuccessResponse>() {
                            @Override
                            public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                                SuccessResponse success = response.body();

                                f.setInviteSent(true);

                                Log.d(TAG, "onResponse: success = " + success);
                            }

                            @Override
                            public void onFailure(Throwable t) {

                                Log.e(TAG, "onFailure: error add friend to book = " + Log.getStackTraceString(t));

                                Snackbar.make(findViewById(android.R.id.content), R.string.error_connection, Snackbar.LENGTH_INDEFINITE).show();
                            }
                        });
                    }

                    adapter.addItems(selectedItems);
                    binding.setShowSplit(false);
                }
                else
                    binding.setShowSplit(true);

            }
        });

        FragmentUtils.openFragment(select, R.id.content_frame, SelectFriendsFragment.TAG, this, true);
    }
}
