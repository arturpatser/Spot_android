package com.gridyn.potspot.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.gridyn.potspot.BitmapHelper;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.query.EnableHostQuery;
import com.gridyn.potspot.response.UserEnableHostResponse;
import com.gridyn.potspot.service.UserService;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class VerificationActivity extends AppCompatActivity {

    private String mEncodedImage;
    private UserService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        initToolbar();
        initRetrofit();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.ver_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = retrofit.create(UserService.class);
    }

    public void onClickCamera(View view) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constant.CAMERA);
    }

    public void onClickGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Constant.GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == Constant.CAMERA && data != null) {
                final Bitmap thumbnailBitmap = BitmapHelper.scaleBitmap((Bitmap) data.getExtras().get("data"));
                mEncodedImage = Constant.URL_BASE64 + BitmapHelper.encodeToString(thumbnailBitmap);
                Log.i("verification", mEncodedImage);
                sendRequest();
            } else if (requestCode == Constant.GALLERY && data != null) {
                Uri selectedImage = data.getData();
                final Bitmap thumbnailBitmap;
                thumbnailBitmap = BitmapHelper
                        .scaleBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage));
                mEncodedImage = Constant.URL_BASE64 + BitmapHelper.encodeToString(thumbnailBitmap);
                Log.i("verification", mEncodedImage);
                sendRequest();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRequest() {
        final EnableHostQuery query = new EnableHostQuery();
        query.token = Person.getToken();
        query.upload[0] = mEncodedImage;
        Call<UserEnableHostResponse> call = mService.enableHost(query);
        call.enqueue(new Callback<UserEnableHostResponse>() {
            @Override
            public void onResponse(Response<UserEnableHostResponse> response, Retrofit retrofit) {
                if (response.body().success) {
                    Snackbar.make(findViewById(android.R.id.content), "Your ID is under review now", Snackbar.LENGTH_LONG).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            finish();
                        }
                    }).start();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Phone is not verified.", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
