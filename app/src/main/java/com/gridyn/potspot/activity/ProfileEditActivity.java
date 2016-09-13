package com.gridyn.potspot.activity;

import android.app.Activity;
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

import com.google.gson.Gson;
import com.gridyn.potspot.BitmapHelper;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.FastBlur;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.query.UserUpdateQuery;
import com.gridyn.potspot.response.UserInfoResponse;
import com.gridyn.potspot.response.UserUpdateResponse;
import com.gridyn.potspot.service.UserService;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.gridyn.potspot.Constant.BASE_IMAGE;

public class ProfileEditActivity extends AppCompatActivity {

    private static final String TAG = ProfileEditActivity.class.getName();
    private CircleImageView mAvatar;
    private TextView mName;
    private UserService mService;
    private TextView mBirthDay;
    private String mGender;
    private TextView mEmail;
    private TextView mPhone;
    private TextView mAboutMe;
    private Spinner mGenderSpinner;

    private boolean mWaitingForVerify;
    private boolean flagForAvatar;
    private String mEncodedAvatar;
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
        mGenderSpinner = (Spinner) findViewById(R.id.profile_edit_gender_spinner);
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGender = mGenderSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initFields() {
        mAvatar = (CircleImageView) findViewById(R.id.profile_edit_image);
        mBirthDay = (TextView) findViewById(R.id.profile_edit_birthday);
        mEmail = (TextView) findViewById(R.id.profile_edit_tv_email);
        mName = (TextView) findViewById(R.id.profile_edit_name);
        mAboutMe = (TextView) findViewById(R.id.profile_edit_tv_about_me);
        mPhone = (TextView) findViewById(R.id.profile_edit_tv_phone);
        mGenderSpinner = (Spinner) findViewById(R.id.profile_edit_gender_spinner);
    }

    private void initRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
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
        if (!mWaitingForVerify && !Person.isHost()) {
            final Intent intent = new Intent(this, VerificationActivity.class);
            startActivity(intent);
        } else if (Person.isHost()) {
            Snackbar.make(findViewById(android.R.id.content), "You are already a host", Snackbar.LENGTH_LONG).show();
        } else if (mWaitingForVerify) {
            Snackbar.make(findViewById(android.R.id.content), "Your request is processed", Snackbar.LENGTH_LONG).show();
        }
    }

    private void initHeader() {
        final ImageView header = (ImageView) findViewById(R.id.profile_edit_header);
        FastBlur.setBackgroundBlur(header, getApplicationContext(),
                "images/chairs.jpg", getResources());
    }

    public void onClickBirthday(final View view) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                final String birthday =
                        dayOfMonth + " "
                                + MONTH[monthOfYear]
                                + " " + year;
                mBirthDay.setText(birthday);
            }
        };
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                callback, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setAccentColor(getResources().getColor(R.color.mainRed));
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    public void onClickSaveEditProfile(final View view) {
        final UserUpdateQuery query = new UserUpdateQuery();
        query.token = Person.getToken();
        if (!mAboutMe.getText().toString().trim().equals("Set")) {
            query.about = mAboutMe.getText().toString().trim();
        }
        if (!mBirthDay.getText().toString().trim().equals("Set")) {
            query.birthday = mBirthDay.getText().toString();
        }
        query.gender = mGender;
        if (!mPhone.getText().toString().trim().equals("Set")) {
            query.phone = mPhone.getText().toString().trim();
        }
        query.email = mEmail.getText().toString().trim();
        query.name = mName.getText().toString().trim();

        if (mEncodedAvatar != null) {
            query.upload.add(0, Constant.URL_BASE64 + mEncodedAvatar);
            if (query.upload.size() > 1) {
                for (int i = 1; i < query.upload.size(); i++) {
                    query.upload.remove(i);
                }
            }
        }

        Call<UserUpdateResponse> call = mService.updateUser(query);
        call.enqueue(new Callback<UserUpdateResponse>() {
            @Override
            public void onResponse(Response<UserUpdateResponse> response, Retrofit retrofit) {
                Log.i(Constant.LOG, "Response code: " + response.code());
                Log.i(Constant.LOG, "JSON QUERY\n" + new Gson().toJson(query));
                UserUpdateResponse res = response.body();
                if (res.success) {
                    Log.i(Constant.LOG, "Ответ ушёл");
                    finish();
                } else {
                    Snackbar.make(view, "Incorrect fields", Snackbar.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(view, Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickPhone(View view) {
//        final View dialogView = getLayoutInflater()
//                .inflate(R.layout.dialog_input_field, (ViewGroup) findViewById(R.id.dialog_input_field));
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        final EditText inputField = (EditText) dialogView.findViewById(R.id.profile_edit_input_field);
//        inputField.setInputType(InputType.TYPE_CLASS_PHONE);
//        if (!mPhone.getText().toString().equals(getResources().getString(R.string.set))) {
//            inputField.setText(mPhone.getText().toString());
//        }
//        builder.setTitle(R.string.phone);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (!inputField.getText().toString().isEmpty()) {
//                    mPhone.setText(inputField.getText().toString().trim());
//                }
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.setView(dialogView);
//        AlertDialog dialog = builder.create();
//        dialog.show();

        startActivityForResult(new Intent(this, VerifyPhoneNumber.class), 123);
    }

    public void onClickAboutMe(View view) {
        final View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_input_field, (ViewGroup) findViewById(R.id.dialog_input_field));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText inputField = (EditText) dialogView.findViewById(R.id.profile_edit_input_field);
        if (!mAboutMe.getText().toString().equals(getResources().getString(R.string.set))) {
            inputField.setText(mAboutMe.getText().toString());
        }
        builder.setTitle(R.string.about_me);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!inputField.getText().toString().isEmpty()) {
                    mAboutMe.setText(inputField.getText().toString().trim());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!inputField.getText().toString().isEmpty()) {
                    mName.setText(inputField.getText().toString().trim());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
        final View editAvatarView = getLayoutInflater().inflate(R.layout.dialog_image,
                (ViewGroup) findViewById(R.id.dialog_image));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(editAvatarView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        ListView list = (ListView) editAvatarView.findViewById(R.id.dialog_image);
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new String[]{"Camera", "Gallery"}));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Constant.CAMERA) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, Constant.CAMERA);
                    dialog.cancel();
                } else if (position == Constant.GALLERY) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, Constant.GALLERY);
                    dialog.cancel();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == Constant.CAMERA && data != null) {
                final Bitmap thumbnailBitmap = BitmapHelper.scaleBitmap((Bitmap) data.getExtras().get("data"));
                mAvatar.setImageBitmap(thumbnailBitmap);
                mEncodedAvatar = BitmapHelper.encodeToString(thumbnailBitmap);
            } else if (requestCode == Constant.GALLERY && data != null) {
                Uri selectedImage = data.getData();
                final Bitmap thumbnailBitmap = BitmapHelper
                        .scaleBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage));
                mAvatar.setImageBitmap(thumbnailBitmap);
                mEncodedAvatar = BitmapHelper.encodeToString(thumbnailBitmap);
                Log.i("profileEdit", "EncodedAvatar: \n\n" + mEncodedAvatar);
            } else if ( requestCode == 123) {

                if (resultCode == Activity.RESULT_OK) {

                    Log.d(TAG, "onActivityResult: received data = " + data.getStringExtra("phone"));

                    mPhone.setText(data.getStringExtra("phone"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Map<String, String> mapToken = new HashMap<>();
        mapToken.put("token", Person.getToken());
        Call<UserInfoResponse> call = mService.getUserInfo(Person.getId(), mapToken);
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(retrofit.Response<UserInfoResponse> response, Retrofit retrofit) {
                UserInfoResponse.Message message = response.body().message.get(0);
                mName.setText(message.data.name);
                if (!message.data.about.isEmpty()) {
                    mAboutMe.setText(message.data.about);
                } else {
                    mAboutMe.setText("Set");
                }
                if (message.data.gender.equals("female")) {
                    mGenderSpinner.setSelection(1);
                } else {
                    mGenderSpinner.setSelection(0);
                }
                if (!message.data.birthday.isEmpty()) {
                    mBirthDay.setText(message.data.birthday);
                } else {
                    mBirthDay.setText("Set");
                }
                mEmail.setText(message.data.email);
                if (message.data.phone != null) {
                    if (!message.data.phone.isEmpty()) {
                        mPhone.setText(message.data.phone);
                    } else {
                        mPhone.setText("Set");
                    }
                }
                mWaitingForVerify = message.system.waitingForVerify;
                if (!flagForAvatar) {
                    flagForAvatar = true;
                    if (message.data.imgs.size() != 0) {
                        if (!message.data.imgs.get(0).isEmpty()) {
                            Picasso.with(getApplicationContext())
                                    .load(Constant.URL_IMAGE + message.data.imgs.get(0))
                                    .into(mAvatar);
                        }
                    } else {
                        Picasso.with(getApplicationContext())
                                .load(BASE_IMAGE)
                                .into(mAvatar);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
