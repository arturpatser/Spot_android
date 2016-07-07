package com.example.gridyn.potspot.activity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gridyn.potspot.R;

public class FeedbackActivity extends AppCompatActivity {

    private EditText mSubject;
    private EditText mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initToolbar();
        setFonts();
        initFields();
    }

    private void initFields() {
        mSubject = (EditText) findViewById(R.id.feedback_subject);
        mDescription = (EditText) findViewById(R.id.feedback_description);
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
            String subject = mSubject.getText().toString();
            String description = mDescription.getText().toString();

            if(subject.isEmpty() || description.isEmpty()) {
                Snackbar.make(item.getActionView(), "Input all fields", Snackbar.LENGTH_SHORT).show();

                //TODO: retrofit

            } else {
                View layout = getLayoutInflater().inflate(R.layout.dialog_feedback,
                        (ViewGroup) findViewById(R.id.feedback_dialog));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(layout);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
