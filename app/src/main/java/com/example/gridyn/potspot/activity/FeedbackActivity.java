package com.example.gridyn.potspot.activity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gridyn.potspot.R;

public class FeedbackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initToolbar();
        setFonts();
    }

    private void setFonts() {
        AssetManager asset = getAssets();
        final TextView title = (TextView) findViewById(R.id.feedback_title);
        final TextView todo = (TextView) findViewById(R.id.feedback_todo);

        title.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        todo.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.feedback_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feedback_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.feedback_send) {
            View layout = getLayoutInflater().inflate(R.layout.dialog_feedback,
                    (ViewGroup) findViewById(R.id.feedback_dialog));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(layout);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
