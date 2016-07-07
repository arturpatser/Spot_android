package com.example.gridyn.potspot.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gridyn.potspot.R;

public class PaymentActivity extends AppCompatActivity {

    private TextView mEmail;
    private TextView mCreditCard;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(creditCard);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
