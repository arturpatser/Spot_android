package com.gridyn.potspot.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.databinding.FragmentEnterCodeBinding;
import com.gridyn.potspot.query.PhoneConfirmQuery;
import com.gridyn.potspot.response.PhoneConfirmResponse;
import com.gridyn.potspot.service.UserService;
import com.gridyn.potspot.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class EnterCodeFragment extends Fragment {

    private static final String ARG_RIGHT_PART = "rp";
    private static final String ARG_LEFT_PART = "lp";
    public static final String TAG = EnterCodeFragment.class.getName();

    String rightPart, leftPart;
    private UserService mService;

    @BindView(R.id.code)
    EditText code;

    public EnterCodeFragment() {
        // Required empty public constructor
    }

    public static EnterCodeFragment newInstance(String leftPart, String rightPart) {
        EnterCodeFragment fragment = new EnterCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LEFT_PART, leftPart);
        args.putString(ARG_RIGHT_PART, rightPart);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            rightPart = getArguments().getString(ARG_RIGHT_PART);
            leftPart = getArguments().getString(ARG_LEFT_PART);

            Log.d(TAG, "onCreate: received phone = " + leftPart + " " + rightPart);
        }

        initRetrofit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_enter_code, container, false);

        ButterKnife.bind(this, rootView);

        FragmentEnterCodeBinding binding = DataBindingUtil.bind(rootView);

        binding.setLeftPart(leftPart);
        binding.setRightPart(rightPart);
        binding.setOnChangeClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.closeFragment(getContext(), TAG);
            }
        });

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.confirm_phone_number, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initRetrofit() {

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = retrofit.create(UserService.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.confirm) {

            Log.d(TAG, "onOptionsItemSelected: send code clicked");

            PhoneConfirmQuery phoneConfirmQuery = new PhoneConfirmQuery();

            phoneConfirmQuery.setPhone(leftPart.concat(rightPart));
            phoneConfirmQuery.setToken(Person.getToken());
            phoneConfirmQuery.setCode(code.getText().toString());

            if (!code.getText().toString().isEmpty()) {

                Call<PhoneConfirmResponse> call = mService.confirmPhone(phoneConfirmQuery);

                call.enqueue(new Callback<PhoneConfirmResponse>() {
                    @Override
                    public void onResponse(Response<PhoneConfirmResponse> response, Retrofit retrofit) {

                        PhoneConfirmResponse resp = response.body();

                        Log.d(TAG, "onResponse: " + resp);

                        if (resp != null && resp.success) {

                            Intent intent = new Intent();
                            intent.putExtra("phone", leftPart.concat(rightPart));
                            getActivity().setResult(getActivity().RESULT_OK, intent);
                            getActivity().finish();
                        } else {

                            Log.e(TAG, "error = " );
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                        Log.e(TAG, "onFailure: error while confirm code = " + Log.getStackTraceString(t));
                    }
                });
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
