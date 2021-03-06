package com.gridyn.potspot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.fragment.EnterCodeFragment;
import com.gridyn.potspot.query.PhoneVerifyQuery;
import com.gridyn.potspot.response.PhoneVerifyResponse;
import com.gridyn.potspot.service.UserService;
import com.gridyn.potspot.utils.FragmentUtils;
import com.gridyn.potspot.utils.WindowUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class VerifyPhoneNumber extends AppCompatActivity {

    private static final String TAG = VerifyPhoneNumber.class.getName();
    private UserService mService;

    @BindView(R.id.left)
    EditText leftPart;

    @BindView(R.id.right)
    EditText rightPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        ButterKnife.bind(this);

        initRetrofit();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            finish();
            }
        });

        leftPart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 3)
                    rightPart.requestFocus();
            }
        });
    }

    private void initRetrofit() {

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = retrofit.create(UserService.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.verify_phone_number, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.verify) {

            Log.d(TAG, "onOptionsItemSelected: verify clicked");

            final String left = leftPart.getText().toString();
            final String right = rightPart.getText().toString();

            if (!left.isEmpty() && !right.isEmpty()) {

                PhoneVerifyQuery phoneVerifyQuery = new PhoneVerifyQuery();
                phoneVerifyQuery.setPhone(left.concat(right));
                phoneVerifyQuery.setToken(Person.getToken());

                Call<PhoneVerifyResponse> call = mService.verifyPhone(phoneVerifyQuery);

                call.enqueue(new Callback<PhoneVerifyResponse>() {
                    @Override
                    public void onResponse(Response<PhoneVerifyResponse> response, Retrofit retrofit) {

                        PhoneVerifyResponse resp = response.body();

                        Log.d(TAG, "onResponse: " + resp);

                        if (resp.success) {

                            //TODO show code confirm layout

                            EnterCodeFragment enterCodeFragment = EnterCodeFragment.newInstance(left, right);

                            FragmentUtils.openFragment(enterCodeFragment, R.id.root_frame, EnterCodeFragment.TAG,
                                    VerifyPhoneNumber.this, true);

                            WindowUtils.hideKeyboardFrom(VerifyPhoneNumber.this, rightPart);
                        } else {

                            //TODO show error while send data
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                        Log.e(TAG, "onFailure: server error while send code = " + Log.getStackTraceString(t));
                    }
                });
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
