package com.example.gridyn.potspot.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gridyn.potspot.Constant;
import com.example.gridyn.potspot.FastBlur;
import com.example.gridyn.potspot.Person;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.query.UserUpdateQuery;
import com.example.gridyn.potspot.response.UserUpdateResponse;
import com.example.gridyn.potspot.service.UserService;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ProfileEditActivity extends AppCompatActivity {

    private CircleImageView mAvarar;
    private TextView mName;
    private UserService mService;
    private TextView mBirthDay;
    private String mGender;
    private TextView mEmail;
    private TextView mPhone;
    private TextView mAboutMe;
    private final String[] MONTH = new String[]{
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initFields();
        initToolbar();
        initHeader();
        initRetrofit();

        setGender();
    }

    private void setGender() {
        final Spinner spinner = (Spinner) findViewById(R.id.profile_edit_gender_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGender = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initFields() {
        final Bundle extra = getIntent().getExtras();
        mAvarar = (CircleImageView) findViewById(R.id.profile_edit_image);
        mBirthDay = (TextView) findViewById(R.id.profile_edit_birthday);
        mEmail = (TextView) findViewById(R.id.profile_edit_tv_email);
        mName = (TextView) findViewById(R.id.profile_edit_name);
        mAboutMe = (TextView) findViewById(R.id.profile_edit_tv_about_me);
        mPhone = (TextView) findViewById(R.id.profile_edit_tv_phone);

        String name  = extra.getString("name");
        String about = extra.getString("about");
        String gender = extra.getString("gender");
        String birthday = extra.getString("birthday");
        String email = extra.getString("email");
        String phone = extra.getString("phone");

        if(!name.isEmpty()) {
            mName.setText(name);
        }

        if(!about.isEmpty()) {
            mAboutMe.setText(about);
        }

        if(!gender.isEmpty()) {
            //TODO: select gender
        }

        if(!birthday.isEmpty()) {
            mBirthDay.setText(birthday);
        }

        if(!email.isEmpty()) {
            mEmail.setText(email);
        }

        if(!phone.isEmpty()) {
            mPhone.setText(phone);
        }
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = retrofit.create(UserService.class);
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.profile_edit_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onClickPaymentInformation(View view) {
        final Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }

    public void onClickEditRealID(View view) {
        final Intent intent = new Intent(this, VerificationActivity.class);
        startActivity(intent);
    }

    private void initHeader() {
        final ImageView header = (ImageView) findViewById(R.id.profile_edit_header);
        FastBlur.setBackgroundBlur(header, getApplicationContext(),
                "images/chairs.jpg", getResources());
    }

    public void onClickBirthday(final View view) {
        final View calendarView = getLayoutInflater().inflate(R.layout.dialog_calendar,
                (ViewGroup) findViewById(R.id.dialog_calendar));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText year = (EditText) calendarView.findViewById(R.id.calendar_year);
        final MaterialCalendarView calendar = (MaterialCalendarView) calendarView.findViewById(R.id.calendar_cal);
        year.setText("19");
        builder.setView(calendarView);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final CalendarDay calendarDay = calendar.getCurrentDate();
                final String birthday = calendar.getSelectedDate().getDay() + " "
                        + MONTH[calendar.getSelectedDate().getMonth()]
                        + " " + calendar.getSelectedDate().getYear();
                mBirthDay.setText(birthday);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*NOP*/}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() == 4) {
                    calendar.state().edit()
                            .setMinimumDate(CalendarDay.from(Integer.parseInt(s.toString())-1, 1, 1))
                            .setMaximumDate(CalendarDay.from(Integer.parseInt(s.toString()), 12, 30))
                    .commit();
                    Log.i("calendar", "Year: " + s);

                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onClickSaveEditProfile(final View view) {
        UserUpdateQuery query = new UserUpdateQuery();

        Call<UserUpdateResponse> call = mService.updateUser(Person.getToken(), query);
        call.enqueue(new Callback<UserUpdateResponse>() {
            @Override
            public void onResponse(Response<UserUpdateResponse> response, Retrofit retrofit) {
                UserUpdateResponse res = response.body();
                if(res.success) {

                } else {
                    Snackbar.make(view, "Incorrect fields", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        finish();
    }

    public void onClickEmail(View view) {
        final View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_input_field, (ViewGroup) findViewById(R.id.dialog_input_field));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText inputField = (EditText) dialogView.findViewById(R.id.profile_edit_input_field);
        inputField.setText(mEmail.getText().toString());
        if(!mEmail.getText().toString().equals(getResources().getString(R.string.email))){
            builder.setTitle(R.string.email);
        }
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!inputField.getText().toString().isEmpty()) {
                    mEmail.setText(inputField.getText().toString().trim());
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClickPhone(View view) {
        final View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_input_field, (ViewGroup) findViewById(R.id.dialog_input_field));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText inputField = (EditText) dialogView.findViewById(R.id.profile_edit_input_field);
        inputField.setInputType(InputType.TYPE_CLASS_PHONE);
        if(!mPhone.getText().toString().equals(getResources().getString(R.string.phone))){
            inputField.setText(mPhone.getText().toString());
        }
        builder.setTitle(R.string.phone);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!inputField.getText().toString().isEmpty()) {
                    mPhone.setText(inputField.getText().toString().trim());
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClickAboutMe(View view) {
        final View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_input_field, (ViewGroup) findViewById(R.id.dialog_input_field));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText inputField = (EditText) dialogView.findViewById(R.id.profile_edit_input_field);
        if(!mAboutMe.getText().toString().equals(getResources().getString(R.string.write_some_words))){
            inputField.setText(mAboutMe.getText().toString());
        }
        builder.setTitle(R.string.about_me);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!inputField.getText().toString().isEmpty()) {
                    mAboutMe.setText(inputField.getText().toString().trim());
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClickEditName(View view) {
        final View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_input_field, (ViewGroup) findViewById(R.id.dialog_input_field));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText inputField = (EditText) dialogView.findViewById(R.id.profile_edit_input_field);
        inputField.setText(mName.getText().toString());
        builder.setTitle(R.string.your_name);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!inputField.getText().toString().isEmpty()){
                    mName.setText(inputField.getText().toString().trim());
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClickProfileEditAvatar(View view) {
        View editAvatarView = getLayoutInflater().inflate(R.layout.dialog_edit_avatar,
                (ViewGroup) findViewById(R.id.dialog_edit_avatar));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(editAvatarView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        ListView list = (ListView) editAvatarView.findViewById(R.id.dialog_edit_avatar);
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new String[]{"Camera", "Gallery"}));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == Constant.CAMERA) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, Constant.CAMERA_RESULT);
                    dialog.cancel();
                }
                else if(position == Constant.GALLERY) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, Constant.GALLERY_RESULT);
                    dialog.cancel();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.CAMERA_RESULT && data != null) {
                Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
                mAvarar.setImageBitmap(thumbnailBitmap);
        }
        else if(requestCode == Constant.GALLERY_RESULT && data != null) {
            try {
                    Uri selectedImage = data.getData();
                    Bitmap thumbnailBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    mAvarar.setImageBitmap(thumbnailBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
