package com.gridyn.potspot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;

public class MyMoneyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(Person.isHost() ? R.layout.activity_my_money_host : R.layout.activity_my_money_no_host);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(Person.isHost() ? getString(R.string.my_money) : "");
    }

    public void onBecomeHostClick(View view) {

        if (!Person.isHost()) {
            Snackbar.make(view, "Your account is not verified", Snackbar.LENGTH_INDEFINITE)
                    .setAction("goto verify", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Intent intent = new Intent(MyMoneyActivity.this, VerificationActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.mainRed))
                    .show();
        } else if (Person.isHost()) {
            startActivity(new Intent(this, SpaceActivity.class));
        }
    }
}
