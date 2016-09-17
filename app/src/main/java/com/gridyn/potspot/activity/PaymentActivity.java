package com.gridyn.potspot.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.query.AddCardQuery;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.utils.ServerApiUtil;
import com.gridyn.potspot.utils.SharedPrefsUtils;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = PaymentActivity.class.getName();
    private TextView mCreditCard;
    private EditText mCardNumber, mCvv, mYear, mMonth;
    TextView currentCard;
    Snackbar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initToolbar();

        mCreditCard = (TextView) findViewById(R.id.payment_credit_card);
        currentCard = (TextView) findViewById(R.id.current_card);

        String userCurrentCard = SharedPrefsUtils.getStringPreference(this, Constant.USER_CARD);

        if (userCurrentCard == null)
            currentCard.setText(R.string.credit_card);
        else {
            String first = userCurrentCard.substring(0,4);
            String last = userCurrentCard.substring(12,16);
            currentCard.setText(getString(R.string.credit_card) + ": " + first + "****" + last);
        }

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.payment_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onClickSavePayment(View view) {
        //TODO: retrofit
        finish();
    }

    public void onClickAddCreditCard(View view) {
        //TODO: view for alert dialog
        addCard();
    }

    private void addCard() {

        final View creditCard = getLayoutInflater()
                .inflate(R.layout.dialog_add_credit_card, (ViewGroup) findViewById(R.id.payment_dialog));
        listenFocus(creditCard);
        progress = Snackbar.make(findViewById(android.R.id.content), R.string.activating_card, Snackbar.LENGTH_INDEFINITE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(creditCard);
        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (true) {
                    try {
                        Stripe stripe = new Stripe(Constant.STRIPE_KEY);

                        final String cardNumber = mCardNumber.getText().toString();
                        final String month = mMonth.getText().toString();
                        final String year = mYear.getText().toString();
                        final String cvv = mCvv.getText().toString();

                        Log.d(TAG, "onClick: card num = " + cardNumber + " m = " + month + " y = " + year + " cvv = " + cvv);

                        Card card = new Card(cardNumber, Integer.parseInt(month), Integer.parseInt(year), cvv);

                        if (checkCard()) {

                            progress.show();
                            stripe.createToken(card,
                                    new TokenCallback() {
                                        @Override
                                        public void onError(Exception error) {

                                            Log.e(TAG, "onError: error while create token for stripe = " + Log.getStackTraceString(error));

                                            progress.setText(getString(R.string.error_card_data));
                                            progress.setAction(getString(R.string.try_again), new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    dialog.dismiss();

                                                    addCard();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onSuccess(Token token) {

                                            Log.d(TAG, "onSuccess: successfull received stripe token = " + token.getId());

                                            Call<SuccessResponse> call = ServerApiUtil.initUser()
                                                    .addCard(Person.getId(), new AddCardQuery(Person.getToken(),
                                                            token.getId()));

                                            call.enqueue(new Callback<SuccessResponse>() {
                                                @Override
                                                public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                                                    SuccessResponse success = response.body();

                                                    Log.d(TAG, "onResponse: success = " + success);

                                                    if (success == null) {

                                                    } else {

                                                        progress.setText(getString(R.string.successfull_add_card));
                                                        progress.setDuration(Snackbar.LENGTH_SHORT);
                                                        progress.show();

                                                        SharedPrefsUtils.setStringPreference(PaymentActivity.this, Constant.USER_CARD, cardNumber);
                                                        SharedPrefsUtils.setStringPreference(PaymentActivity.this, Constant.USER_CARD_M, month);
                                                        SharedPrefsUtils.setStringPreference(PaymentActivity.this, Constant.USER_CARD_Y, year);
                                                        SharedPrefsUtils.setStringPreference(PaymentActivity.this, Constant.USER_CARD_C, cvv);
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Throwable t) {

                                                    Log.e(TAG, "onFailure: error while add card = " + Log.getStackTraceString(t));

                                                    progress.setText(getString(R.string.error_connection));
                                                    progress.setAction(getString(R.string.try_again), new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            addCard();
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                        }

                    } catch (AuthenticationException e) {
                        Log.e(TAG, "onClickBuyPay: error while init stripe = " + Log.getStackTraceString(e));
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content),
                            "Input please correct information", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        Button neutral_button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        Button positive_button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);


        if (neutral_button != null) {


            neutral_button.setTextColor(getResources()
                    .getColor(android.R.color.black));
        }
        if (positive_button != null) {

            positive_button.setTextColor(getResources()
                    .getColor(android.R.color.black));
        }

    }

    private boolean checkCard() {
//        String[] date = mDate.getText().toString().trim().split("/");
        return !mCardNumber.getText().toString().isEmpty() &&
                !mCvv.getText().toString().isEmpty() &&
                !mMonth.getText().toString().isEmpty() &&
                !mYear.getText().toString().isEmpty() &&
                mCardNumber.getText().toString().length() == 16 &&
                mCvv.getText().toString().length() == 3;
    }

    private void listenFocus(View creditCard) {
        mCardNumber = (EditText) creditCard.findViewById(R.id.card_number);
        mCvv = (EditText) creditCard.findViewById(R.id.card_cvv);
        mMonth = (EditText) creditCard.findViewById(R.id.card_month);
        mYear = (EditText) creditCard.findViewById(R.id.card_year);

        mCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*NOP*/}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*NOP*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 16) {
                    mCvv.requestFocus();
                }
            }
        });

        mCvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*NOP*/}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {/*NOP*/}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 3) {
                    mMonth.requestFocus();
                }
            }
        });

        mMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*NOP*/}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2) {
                    mYear.requestFocus();
                }
            }
        });
    }
}
