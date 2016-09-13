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
import android.widget.EditText;
import android.widget.TextView;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;
import com.stripe.android.model.Card;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = PaymentActivity.class.getName();
    private TextView mEmail;
    private TextView mCreditCard;
    private EditText mCardNumber, mCvv, mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initToolbar();

        mEmail = (TextView) findViewById(R.id.payment_email);
        mCreditCard = (TextView) findViewById(R.id.payment_credit_card);

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
        final View creditCard = getLayoutInflater()
                .inflate(R.layout.dialog_add_credit_card, (ViewGroup) findViewById(R.id.payment_dialog));
        listenFocus(creditCard);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(creditCard);
        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (true) {
                    try {
                        Stripe stripe = new Stripe(Constant.STRIPE_KEY);

                        Card card = new Card("4242424242424242", 12, 2017, "123");

                        stripe.createToken(card,
                                new TokenCallback() {
                                    @Override
                                    public void onError(Exception error) {

                                        Log.e(TAG, "onError: error while create token for stripe = " + Log.getStackTraceString(error));
                                    }

                                    @Override
                                    public void onSuccess(Token token) {

                                        Log.d(TAG, "onSuccess: successfull received stripe token = " + token.getId());
                                    }
                                });

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
        alertDialog.show();
    }

    private boolean checkCard() {
//        String[] date = mDate.getText().toString().trim().split("/");
        return !mCardNumber.getText().toString().isEmpty() &&
                !mCvv.getText().toString().isEmpty() &&
                !mDate.getText().toString().isEmpty() &&
                Integer.valueOf(mCardNumber.getText().toString().trim()) == 8 &&
                Integer.valueOf(mCvv.getText().toString().trim()) == 3;
//                Integer.valueOf(date[0].concat(date[1])) == 4;
    }

    private void listenFocus(View creditCard) {
        mCardNumber = (EditText) creditCard.findViewById(R.id.card_number);
        mCvv = (EditText) creditCard.findViewById(R.id.card_cvv);
        mDate = (EditText) creditCard.findViewById(R.id.card_date);

        mCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*NOP*/}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {/*NOP*/}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 8) {
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
                    mDate.requestFocus();
                }
            }
        });

        mDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*NOP*/}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {/*NOP*/}

            @Override
            public void afterTextChanged(Editable s) {
//                if(s.length() == 2) {
//                    mDate.append("/");
//                } else if(s.length() == 3) {
//                    String date = mDate.getText().toString().trim();
//                    char[] ch = date.toCharArray();
//                    mDate.setText(ch[0] + ch[1]);
//                }
            }
        });
    }
}
