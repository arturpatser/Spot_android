package com.gridyn.potspot.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gridyn.potspot.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifTextView;

public class FirstLaunchFragment extends Fragment {

    private static final String ARG_PARAM1 = "gif_code";

    private int gifResId;

    GifTextView gifImageView;

    public FirstLaunchFragment() {
        // Required empty public constructor
    }

    public static FirstLaunchFragment newInstance(int gifResource) {
        FirstLaunchFragment fragment = new FirstLaunchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, gifResource);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            gifResId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_first_launch, container, false);

        gifImageView = (GifTextView) rootView.findViewById(R.id.gif_view);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getResources(), gifResId);
            gifDrawable.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gifImageView.setBackground(gifDrawable);
    }
}
