package com.gridyn.potspot.activity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gridyn.potspot.R;

public class InviteFriendActivity extends AppCompatActivity {

    private TextView mTitle;
    private EditText mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        mTitle = (TextView) findViewById(R.id.friend_title);
        mEmail = (EditText) findViewById(R.id.friend_et_email);

        initToolbar();
        setFonts();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.friend_toolbar);
        toolbar.setNavigationIcon(R.drawable.close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle.setText("");
    }

    private void setFonts() {
        AssetManager asset = getAssets();
        final TextView text = (TextView) findViewById(R.id.friend_text);
        final Button invite = (Button) findViewById(R.id.friend_invite);
        final EditText email = (EditText) findViewById(R.id.friend_et_email);
        mTitle.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        text.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        invite.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        email.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
    }

    public void onClickInvite(View view) {
        String email = mEmail.getText().toString();
        if (email.isEmpty()) {
            Snackbar.make(view, "Input email please", Snackbar.LENGTH_SHORT).show();
        } else {
            //TODO: retrofit
        }
    }
}
