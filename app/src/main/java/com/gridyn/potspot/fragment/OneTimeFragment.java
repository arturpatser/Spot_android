package com.gridyn.potspot.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.gridyn.potspot.R;
import com.gridyn.potspot.utils.FragmentUtils;
import com.gridyn.potspot.utils.SharedPrefsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OneTimeFragment extends Fragment {

    private static final String ARG_SHARED_PREF = "param1";
    private static final String ARG_PIC_ID = "param2";

    private String mSharedPref;
    private int picId;

    @BindView(R.id.pic_to_show)
    SubsamplingScaleImageView imageView;

    public OneTimeFragment() {
        // Required empty public constructor
    }

    public static OneTimeFragment newInstance(String sharedPref, int picId) {
        OneTimeFragment fragment = new OneTimeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHARED_PREF, sharedPref);
        args.putInt(ARG_PIC_ID, picId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSharedPref = getArguments().getString(ARG_SHARED_PREF);
            picId = getArguments().getInt(ARG_PIC_ID);

            if (mSharedPref != null)
                SharedPrefsUtils.setBooleanPreference(getContext(), mSharedPref, true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_one_time, container, false);

        ButterKnife.bind(this, rootView);

        imageView.setImage(ImageSource.resource(picId));

        if (mSharedPref != null)
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.popBackStack(getContext());
            }
        });

        return rootView;
    }
}
