package com.gridyn.potspot.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.gridyn.potspot.BitmapHelper;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.FastBlur;
import com.gridyn.potspot.R;
import com.gridyn.potspot.adapter.ListingEditAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public class SpaceActivity extends AppCompatActivity {

    private EditText mTitle;
    private EditText mDescription;
    private EditText mAddress;
    private EditText mPrice;
    private EditText mGuests;
    private Spinner mListingTypeSpinner;
    private Spinner mTobaccoSpinner;
    private Spinner mHeatedSpinner;
    private Spinner mHandicapSpinner;
    private String mListingType;
    private String mTobacco;
    private String mHeated;
    private String mHandicap;
    private String mEncodedImage;
    private List<String> mEncodedImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);
        initFields();
        initToolbar();
        initHeader();
        initSpinners();
        initRecycler();
    }

    private void initFields() {
        mEncodedImages = new ArrayList<>();
        mTitle = (EditText) findViewById(R.id.space_title);
        mDescription = (EditText) findViewById(R.id.space_desc);
        mAddress = (EditText) findViewById(R.id.space_address);
        mPrice = (EditText) findViewById(R.id.space_price);
        mGuests = (EditText) findViewById(R.id.space_guests);
        mListingTypeSpinner = (Spinner) findViewById(R.id.space_listing_type);
        mTobaccoSpinner = (Spinner) findViewById(R.id.space_tobacco);
        mHeatedSpinner = (Spinner) findViewById(R.id.space_heated);
        mHandicapSpinner = (Spinner) findViewById(R.id.space_handicap);
        setDefaultValues();
    }

    private void initRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.space_recycler);
        ListingEditAdapter adapter = new ListingEditAdapter(mEncodedImages, getApplicationContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void setDefaultValues() {
        mListingType = "";
        mTobacco = "";
        mHeated = "";
        mHandicap = "";
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.space_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initHeader() {
        final FrameLayout header = (FrameLayout) findViewById(R.id.space_header);
        FastBlur.setBackgroundBlur(header, getApplicationContext(),
                "images/chairs.jpg", getResources());
    }

    public void onClickAddPhotoSpace(View view) {
        final View addPhotoView = getLayoutInflater().inflate(R.layout.dialog_image,
                (ViewGroup) findViewById(R.id.dialog_image));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(addPhotoView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        ListView list = (ListView) addPhotoView.findViewById(R.id.dialog_image);
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

    private void initSpinners() {
        initListingType();
        initTobacco();
        initHeated();
        initHandicap();
    }

    private void initListingType() {
        final List<String> stringArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listing_type)));
        final HintSpinner<String> hintSpinner = new HintSpinner<>(mListingTypeSpinner,
                new HintAdapter<>(this, R.string.patio_backyard_balcony_smoking_rooms, stringArray), new HintSpinner.Callback<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                mListingType = itemAtPosition;
                Log.i("space", mListingType);
            }
        });
        hintSpinner.init();
    }

    private void initTobacco() {
        final List<String> stringArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.yes_or_no)));
        final HintSpinner<String> hintSpinner = new HintSpinner<>(mTobaccoSpinner,
                new HintAdapter<>(this, R.string.yes_no, stringArray), new HintSpinner.Callback<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                if (position == 0) {
                    mTobacco = getResources().getString(R.string.tobacco_friendly);
                } else {
                    mTobacco = "";
                }
                Log.i("space", mTobacco);
            }
        });
        hintSpinner.init();
    }

    private void initHeated() {
        final List<String> stringArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.yes_or_no)));
        final HintSpinner<String> hintSpinner = new HintSpinner<>(mHeatedSpinner,
                new HintAdapter<>(this, R.string.yes_no, stringArray), new HintSpinner.Callback<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                if (position == 0) {
                    mHeated = getResources().getString(R.string.heated);
                } else {
                    mHeated = "";
                }

                Log.i("space", mHeated);
            }
        });
        hintSpinner.init();
    }


    private void initHandicap() {
        final List<String> stringArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.yes_or_no)));
        final HintSpinner<String> hintSpinner = new HintSpinner<>(mHandicapSpinner,
                new HintAdapter<>(this, R.string.yes_no, stringArray), new HintSpinner.Callback<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                if (position == 0) {
                    mHandicap = getResources().getString(R.string.handicap_accessible);
                } else {
                    mHandicap = "";
                }
                Log.i("space", mHandicap);
            }
        });
        hintSpinner.init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == Constant.CAMERA && data != null) {
                final Bitmap thumbnailBitmap = BitmapHelper.scaleBitmap((Bitmap) data.getExtras().get("data"));
                mEncodedImages.add(Constant.URL_BASE64 + BitmapHelper.encodeToString(thumbnailBitmap));
//                mEncodedImage = BitmapHelper.encodeToString(thumbnailBitmap);
            } else if (requestCode == Constant.GALLERY && data != null) {
                Uri selectedImage = data.getData();
                final Bitmap thumbnailBitmap = BitmapHelper
                        .scaleBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage));
                mEncodedImages.add(Constant.URL_BASE64 + BitmapHelper.encodeToString(thumbnailBitmap));
//                mEncodedImage = BitmapHelper.encodeToString(thumbnailBitmap);
                Log.i("profileEdit", "EncodedAvatar: \n\n" + mEncodedImages.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onClickCreateSpot(View view) {
        final Intent intent = new Intent(this, ListingSettingActivityNew.class);

        if (mTitle.getText().toString().trim().length() > 3) {
            intent.putExtra("title", mTitle.getText().toString().trim());
        } else {
            Snackbar.make(view, "Title must be at least 3 chars long.", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (mAddress.getText().toString().trim().length() > 3) {
            intent.putExtra("address", mAddress.getText().toString().trim());
        } else {
            Snackbar.make(view, "Address must be at least 3 chars long.", Snackbar.LENGTH_LONG).show();
            return;
        }

        intent.putExtra("description", mDescription.getText().toString().trim());
        intent.putExtra("price", mPrice.getText().toString().trim());
        intent.putExtra("maxGuests", mGuests.getText().toString().trim());
        if (mListingType != null) {
            intent.putExtra("listingType", mListingType);
        } else {
            intent.putExtra("listingType", "");
        }
        intent.putExtra("tobacco", mTobacco);
        intent.putExtra("heated", mHeated);
        intent.putExtra("handicap", mHandicap);
        String[] strings = new String[mEncodedImages.size()];
        intent.putExtra("upload", mEncodedImages.toArray(strings));

        if (isEmptyFields()) {
            Snackbar.make(view, "Input red fields", Snackbar.LENGTH_LONG).show();
        } else {
            startActivity(intent);
        }
    }

    private boolean isEmptyFields() {
        return mTitle.getText().toString().trim().isEmpty() ||
                mAddress.getText().toString().trim().isEmpty() ||
                mPrice.getText().toString().trim().isEmpty();
    }
}
